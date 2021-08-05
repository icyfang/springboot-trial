package com.example.springmvc.utils;

import com.example.springmvc.swagger.UserController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Hodur
 * @date 2021/7/26
 */
public abstract class BaseUnitTestGenerator {

    final String sourcePath = Objects.requireNonNull(BaseUnitTestGenerator.class.getResource("/")).getPath();
    final String TEST_PATH = sourcePath.replace("/target/classes", "/src/test/java");

    final String MAIN_PATH = sourcePath.replace("/target/classes", "/src/main/java");

    final Pattern p1 = Pattern.compile("[<>,]+");
    final Pattern p = Pattern.compile("[^<>,]+");

    final List<String> lines = new ArrayList<>();
    final List<Class<?>> RETURN_SET = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        genUnitTest(UserController.class);
    }

    public static void genUnitTest(Class<?> clazz) throws IOException {

        if (clazz.getAnnotation(Controller.class) != null || clazz.getAnnotation(RestController.class) != null) {
            new WebUnitTestGenerator().doGenerate(clazz);
        } else if (clazz.getAnnotation(Service.class) != null || clazz.getAnnotation(Component.class) != null) {
            new ServiceUnitTestGenerator().doGenerate(clazz);
        }
    }

    public void doGenerate(Class<?> clazz) throws IOException {

        String testClassName = clazz.getSimpleName() + "Test";
        String packageName = clazz.getPackage().getName();
        lines.add("package " + packageName + ";");
        addImport();
        lines.add("@ExtendWith(SpringExtension.class)");
        lines.add("@SpringBootTest");
        lines.add("@TestMethodOrder(MethodOrderer.OrderAnnotation.class)");
        lines.add("@TestInstance(TestInstance.Lifecycle.PER_CLASS)");
        lines.add("public class " + testClassName + "{");
        mockAutoWiredBean(clazz);
        addTestMethod(clazz);
        addInitInstance();
        lines.add("}");

        String packagePath = packageName.replaceAll("\\.", "/");
        String pathname = TEST_PATH + packagePath + "/" + testClassName + ".java";
        FileUtils.writeLines(new File(pathname), lines);
        lines.clear();
        RETURN_SET.clear();
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    private void addInitInstance() {
        for (int i = 0; i < RETURN_SET.size(); i++) {
            initInstance(RETURN_SET.get(i));
        }
    }

    void mockAutoWiredBean(Class<?> clazz) throws IOException {

        List<Field> fieldToMock = addMockBean(clazz);

        lines.add("@BeforeAll");
        lines.add("public void setup() {");
        initMockMvc(clazz);

        String s = readTargetClassFile(clazz);
        Map<Field, Set<String>> maps = new HashMap<>(16);
        for (Field field : fieldToMock) {
            Pattern p = Pattern.compile(field.getName() + "\\.[\\d\\D]*?\\(");
            Matcher m = p.matcher(s);
            while (m.find()) {
                String group = m.group();
                String methodName = group.replace(field.getName() + ".", "").replace("(", "");
                maps.computeIfAbsent(field, i -> new HashSet<>()).add(methodName);
            }
        }
        maps.forEach((field, v) -> v.forEach(methodName -> doMockBean(field, methodName)));
        lines.add("}");
    }

    abstract void initMockMvc(Class<?> clazz);

    private void doMockBean(Field field, String methodName) {

        Method[] methods = field.getType().getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {

                Type genericReturnType = method.getGenericReturnType();
                String r = getString(genericReturnType);
                String collect = Arrays.stream(method.getParameters())
                                       .map(j -> "any()")
                                       .collect(Collectors.joining(", "));
                if (genericReturnType.equals(Void.class)) {
                    lines.add("willDoNothing().given(" + field.getName() + ")." + methodName + "(" + collect + ");");
                } else {
                    lines.add("given(" + field.getName() + "." + methodName + "(" + collect + "))"
                            + ".willReturn" + "(" + r + ");");
                }
            }
        }
    }

    String getString(Type genericReturnType) {
        String r = "null";
        if (genericReturnType instanceof Class) {
            Class<?> genericReturnClass = (Class<?>) genericReturnType;
            r = getReturnInstanceName(genericReturnClass);
        } else if (genericReturnType instanceof ParameterizedTypeImpl) {
            ParameterizedTypeImpl genericReturnClass = (ParameterizedTypeImpl) genericReturnType;
            String returnInstanceName =
                    getString(genericReturnClass.getActualTypeArguments()[0]);
            Class<?> rawType = genericReturnClass.getRawType();
            if (rawType.equals(List.class)) {
                r = "Collections.singletonList(" + returnInstanceName + ")";
            } else if (rawType.equals(Set.class)) {
                r = "Collections.singleton(" + returnInstanceName + ")";
            } else if (rawType.equals(Map.class)) {
                String returnInstanceName1 =
                        getString(genericReturnClass.getActualTypeArguments()[1]);
                r = "Collections.singletonMap(" + returnInstanceName + "," + returnInstanceName1 + ")";
//            } else if (rawType.equals(JsonEntity.class)) {
//                r = "ResponseHelper.of(" + returnInstanceName + ")";
            } else if (rawType.equals(Map.Entry.class)) {
                String returnInstanceName1 =
                        getString(genericReturnClass.getActualTypeArguments()[1]);
                r = "Maps.immutableEntry(" + returnInstanceName + "," + returnInstanceName1 + ")";
            }
        } else {
            lines.add("// todo init the return instance manually");
        }
        return r;
    }

    private String readTargetClassFile(Class<?> clazz) throws IOException {
        String packageName = clazz.getPackage().getName();
        String packagePath = packageName.replaceAll("\\.", "/");
        String pathname = MAIN_PATH + packagePath + "/" + clazz.getSimpleName() + ".java";
        return FileUtils.readFileToString(new File(pathname), "utf-8");
    }

    private String getReturnInstanceName(Class<?> genericReturnClass) {

        String v = getPrimitiveValue(genericReturnClass);
        String nullStr = "null";
        if (!nullStr.equals(v)) {
            return v;
        } else {
            if (!RETURN_SET.contains(genericReturnClass)) {
                RETURN_SET.add(genericReturnClass);
            }
        }
        return instanceMethodName(genericReturnClass);
    }

    private String instanceMethodName(Class<?> genericReturnClass) {
        return "get" + genericReturnClass.getSimpleName() + "Instance()";
    }

    abstract void addTestMethod(Class<?> aClass);

    private void initInstance(Class<?> type) {
        String simpleName = type.getSimpleName();
        String fieldName = "a" + simpleName;
        lines.add(simpleName + " " + instanceMethodName(type) + "{");
        lines.add(simpleName + " " + fieldName + " = new " + simpleName + "();");
        for (Field declaredField : type.getDeclaredFields()) {
            Type genericType = declaredField.getGenericType();
            if (!"serialVersionUID".equals(declaredField.getName()) && !genericType.equals(BigDecimal.class)) {
                lines.add(fieldName + ".set" + switchCaseOfFirstChar(declaredField.getName()) + ("(" + getString(genericType) + ");"));
            }
        }
        lines.add("return " + fieldName + ";");
        lines.add("}");
    }

    String getReturnTypeStr(Type genericReturnType) {
        String typeName = genericReturnType.getTypeName();

        List<String> classNames = new ArrayList<>();
        List<String> operators = new ArrayList<>();

        Matcher matcher = p.matcher(typeName);
        while (matcher.find()) {
            String group = matcher.group();
            classNames.add(group.substring(group.lastIndexOf(".") + 1));
        }
        Matcher matcher1 = p1.matcher(typeName);
        while (matcher1.find()) {
            String group = matcher1.group();
            operators.add(group.substring(group.lastIndexOf(".") + 1));
        }

        if (classNames.size() == operators.size()) {

            StringBuilder returnBuilder = new StringBuilder();
            for (int i = 0; i < classNames.size(); i++) {
                returnBuilder.append(classNames.get(i)).append(operators.get(i));
            }
            return returnBuilder.toString();
        }
        return classNames.get(0);
    }

    String getPrimitiveValue(Class<?> type) {
        String v = "null";
        if (type.isPrimitive()) {
            switch (type.getName()) {
                case "int":
                    v = "1";
                    break;
                case "long":
                    v = "1L";
                    break;
                case "boolean":
                    v = "true";
                    break;
                default:
                    break;
            }
        }
        if (type.equals(Integer.class)) {
            v = "1";
        } else if (type.equals(Long.class)) {
            v = "1L";
        } else if (type.equals(String.class)) {
            v = "\"str\"";
        } else if (type.equals(Boolean.class)) {
            v = "true";
        }
        return v;
    }

    private List<Field> addMockBean(Class<?> clazz) {
        List<Field> fieldToMock = new ArrayList<>();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Autowired.class)) {
                fieldToMock.add(declaredField);
                lines.add("@MockBean");
                lines.add("private " + declaredField.getType().getSimpleName() + " " + declaredField.getName() + ";");
            }
        }

        addTestTarget(clazz);
        return fieldToMock;
    }

    abstract void addTestTarget(Class<?> clazz);

    public String switchCaseOfFirstChar(String str) {

        char[] cs = str.toCharArray();
        char a = 'a';
        if (cs[0] >= a) {
            cs[0] -= 32;
        } else {
            cs[0] += 32;
        }
        return String.valueOf(cs);
    }

    private void addImport() {
        lines.add("import static org.mockito.ArgumentMatchers.any;\n" +
                "import static org.mockito.BDDMockito.given;\n" +
                "import static org.mockito.BDDMockito.willDoNothing;\n" +
                "import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;\n" +
                "import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;\n" +
                "import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;\n" +
                "import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;\n" +
                "import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;\n" +
                "import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;\n" +
                "import org.springframework.boot.test.mock.mockito.MockBean;\n" +
                "import org.springframework.test.web.servlet.MvcResult;\n" +
                "import org.springframework.boot.test.mock.mockito.MockBean;\n" +
                "import org.junit.jupiter.api.MethodOrderer;\n" +
                "import org.junit.jupiter.api.TestInstance;\n" +
                "import org.springframework.context.MessageSource;\n" +
                "import org.springframework.http.MediaType;\n" +
                "import org.junit.jupiter.api.Test;\n" +
                "import org.junit.jupiter.api.BeforeAll;\n" +
                "import org.junit.jupiter.api.extension.ExtendWith;\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import org.springframework.boot.test.context.SpringBootTest;\n" +
                "import org.springframework.test.context.junit.jupiter.SpringExtension;\n" +
                "import org.springframework.test.web.servlet.MvcResult;\n" +
                "import org.springframework.test.web.servlet.MockMvc;\n" +
                "import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;\n" +
                "import org.springframework.test.web.servlet.setup.MockMvcBuilders;\n" +
                "import com.fasterxml.jackson.databind.ObjectMapper;\n" +
                "import com.fasterxml.jackson.core.type.TypeReference;\n" +
                "import com.fasterxml.jackson.databind.ObjectMapper;\n" +
                "import org.mockito.Mockito;\n" +
                "import com.fasterxml.jackson.databind.ObjectMapper;\n" +
                "import java.util.List;\n" +
                "import java.util.Map;\n" +
                "import java.util.Set;\n" +
                "import java.util.Collections;");
    }

    public static class WebUnitTestGenerator extends BaseUnitTestGenerator {

        @Override
        public void initMockMvc(Class<?> clazz) {
            String controllerVariable = switchCaseOfFirstChar(clazz.getSimpleName());
            lines.add("mockMvc = MockMvcBuilders.standaloneSetup(" + controllerVariable + ").build();");
        }

        @Override
        void addTestMethod(Class<?> aClass) {

            String basePath = getBasePath(aClass);
            for (Method declaredMethod : aClass.getDeclaredMethods()) {

                MappingResult mappingResult = parseMapping(declaredMethod);
                if (mappingResult.isMapping()) {

                    lines.add("@Test");
                    lines.add("public void " + declaredMethod.getName() + "() throws Exception{");
                    addRequest(basePath, declaredMethod, mappingResult);
                    lines.add("        MvcResult result = mockMvc.perform(requestBuilder)\n" +
                            "                .andExpect(status().isOk())\n" +
                            "                .andReturn();");
//                    addAssert(declaredMethod);
                    lines.add("}");
                }
            }
        }

        @Override
        void addTestTarget(Class<?> clazz) {
            lines.add("@Autowired");
            String controllerVariable = switchCaseOfFirstChar(clazz.getSimpleName());
            lines.add("private " + clazz.getSimpleName() + " " + controllerVariable + ";");
            lines.add("");
            lines.add("private MockMvc mockMvc;");
        }

        public MappingResult parseMapping(Method declaredMethod) {

            String value = null;
            RequestMethod method = null;
            String produce = null;
            boolean isMapping = false;

            if (declaredMethod.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping mapping = declaredMethod.getAnnotation(RequestMapping.class);
                value = mapping.value()[0];
                method = mapping.method()[0];
                String[] produces = mapping.produces();
                if (produces.length > 0) {
                    produce = produces[0];
                }
                isMapping = true;

            } else if (declaredMethod.isAnnotationPresent(GetMapping.class)) {
                GetMapping mapping = declaredMethod.getAnnotation(GetMapping.class);
                value = mapping.value()[0];
                method = RequestMethod.GET;
                String[] produces = mapping.produces();
                if (produces.length > 0) {
                    produce = produces[0];
                }
                isMapping = true;

            } else if (declaredMethod.isAnnotationPresent(PostMapping.class)) {
                PostMapping mapping = declaredMethod.getAnnotation(PostMapping.class);
                value = mapping.value()[0];
                method = RequestMethod.POST;
                String[] produces = mapping.produces();
                if (produces.length > 0) {
                    produce = produces[0];
                }
                isMapping = true;
            } else if (declaredMethod.isAnnotationPresent(PutMapping.class)) {
                PutMapping mapping = declaredMethod.getAnnotation(PutMapping.class);
                value = mapping.value()[0];
                method = RequestMethod.PUT;
                String[] produces = mapping.produces();
                if (produces.length > 0) {
                    produce = produces[0];
                }
                isMapping = true;
            } else if (declaredMethod.isAnnotationPresent(PatchMapping.class)) {
                PatchMapping mapping = declaredMethod.getAnnotation(PatchMapping.class);
                value = mapping.value()[0];
                method = RequestMethod.PATCH;
                String[] produces = mapping.produces();
                if (produces.length > 0) {
                    produce = produces[0];
                }
                isMapping = true;
            } else if (declaredMethod.isAnnotationPresent(DeleteMapping.class)) {
                DeleteMapping mapping = declaredMethod.getAnnotation(DeleteMapping.class);
                value = mapping.value()[0];
                method = RequestMethod.DELETE;
                String[] produces = mapping.produces();
                if (produces.length > 0) {
                    produce = produces[0];
                }
                isMapping = true;
            }
            return new MappingResult(value, method, produce, isMapping);
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class MappingResult {
            String value = null;
            RequestMethod method = null;
            String produce = null;
            boolean isMapping = false;
        }

        void addAssert(Method declaredMethod) {

            Type genericReturnType = declaredMethod.getGenericReturnType();
            String returnType = getReturnTypeStr(genericReturnType);

            lines.add(returnType + " jsonEntity = new ObjectMapper().readValue(result.getResponse()" +
                    ".getContentAsString(),");
            lines.add("new TypeReference<" + returnType + ">() {");
            lines.add("});");
            lines.add("Assert.assertEquals(jsonEntity.getStatus(), 200);");
        }

        private void addRequest(String basePath, Method declaredMethod, MappingResult mappingResult) {

            Parameter[] parameters = declaredMethod.getParameters();
            Optional<Parameter> first = Arrays.stream(parameters)
                                              .filter(i -> i.isAnnotationPresent(RequestBody.class))
                                              .findFirst();
            Optional<String> s = first.map(i -> getString(i.getType()));

            String requestMethod = StringUtils.lowerCase(mappingResult.getMethod().name());
            String pathStrBuilder = "MockHttpServletRequestBuilder requestBuilder =" + requestMethod +
                    "(\"" + basePath + mappingResult.getValue() + "\"" + getPathVariable(parameters) + ")";
            lines.add(pathStrBuilder);
            addRequestParam(parameters);

            s.ifPresent(i -> lines.add(".content(new ObjectMapper().writeValueAsString("
                    + i + "))"));
            addContentType(mappingResult.getProduce());
        }

        private void addContentType(String produce) {
            String jsonStr = "application/json";
            if (jsonStr.equals(produce) || produce == null) {
                produce = "MediaType.APPLICATION_JSON_VALUE";
                lines.add(".contentType(" + produce + ");");
            } else {
                lines.add(".contentType(\"" + produce + "\");");
            }
        }

        private void addRequestParam(Parameter[] parameters) {

            StringBuilder paramStrBuilder = new StringBuilder();
            Arrays.stream(parameters)
                  .filter(i -> i.isAnnotationPresent(RequestParam.class))
                  .forEach(paramParameter -> {

                      String v = getPrimitiveValue(parameters.getClass());
                      String paramName = paramParameter.getAnnotation(RequestParam.class).value();
                      paramStrBuilder.append(".param(\"").append(paramName).append("\",\"").append(v).append("\")\n");
                  });

            if (paramStrBuilder.length() > 0) {
                lines.add(paramStrBuilder.toString());
            }
        }

        private String getPathVariable(Parameter[] parameters) {

            String pathParams = Arrays.stream(parameters)
                                      .filter(i -> i.isAnnotationPresent(PathVariable.class))
                                      .map(Parameter::getType)
                                      .map(this::getPrimitiveValue).collect(Collectors.joining(", "));
            return pathParams.length() == 0 ? pathParams : ", " + pathParams;
        }

        private String getBasePath(Class<?> aClass) {
            RequestMapping requestMapping = aClass.getAnnotation(RequestMapping.class);
            String basePath = requestMapping.name();
            if (StringUtils.isBlank(basePath)) {
                basePath = requestMapping.value()[0];
            }
            return basePath;
        }
    }

    public static class ServiceUnitTestGenerator extends BaseUnitTestGenerator {

        @Override
        void initMockMvc(Class<?> clazz) {

        }

        @Override
        void addTestMethod(Class<?> aClass) {

            for (Method declaredMethod : aClass.getDeclaredMethods()) {

                if ((declaredMethod.getModifiers() & 1) != 1) {
                    continue;
                }
                lines.add("@Test");
                lines.add("public void " + declaredMethod.getName() + "() {");
                Class<?> returnType = declaredMethod.getReturnType();
                Type[] genericParameterTypes = declaredMethod.getGenericParameterTypes();
                StringBuilder sb = new StringBuilder();
                String r = "a" + returnType.getSimpleName();
                sb.append(getReturnTypeStr(declaredMethod.getGenericReturnType()))
                  .append(" ")
                  .append(r)
                  .append("=")
                  .append(switchCaseOfFirstChar(getSimpleNameRemoveImpl(aClass)))
                  .append(".")
                  .append(declaredMethod.getName())
                  .append("(");
                for (Type parameterType : genericParameterTypes) {
                    sb.append(getString(parameterType)).append(",");
                }
                sb.delete(sb.length() - 1, sb.length());
                sb.append(");");
                lines.add(sb.toString());
                if (returnType.equals(Boolean.class)) {
                    lines.add("assertTrue(" + r + ");");
                } else {
                    lines.add("assertNotNull(" + r + ");");
                }
                lines.add("}");
            }
        }

        @Override
        void addTestTarget(Class<?> clazz) {
            lines.add("@Autowired");
            String simpleName = getSimpleNameRemoveImpl(clazz);
            lines.add(simpleName + " " + switchCaseOfFirstChar(simpleName) + ";");
        }

        private String getSimpleNameRemoveImpl(Class<?> clazz) {
            return clazz.getSimpleName().replace("Impl", "");
        }
    }

}