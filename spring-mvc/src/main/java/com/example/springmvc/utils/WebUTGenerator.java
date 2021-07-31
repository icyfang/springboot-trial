package com.example.springmvc.utils;

import com.example.springmvc.swagger.UserController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Hodur
 * @date 2021/7/26
 */
public class WebUTGenerator {

    /**
     * project name
     */
    private static final String PROJECT_NAME = "spring-mvc";

    /**
     * absolute path of project
     */
    private static final String ROOT_PATH = "";
    private static final String SECONDARY_PATH = "\\src\\test\\java\\";

    private static final List<String> LINES = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        genUnitTest(UserController.class);
    }

    public static void genUnitTest(Class<?> clazz) throws IOException {

        Controller annotation = clazz.getAnnotation(Controller.class);
        if (annotation == null && clazz.getAnnotation(RestController.class) == null) {
            throw new RuntimeException("not a controller");
        }
        String testClassName = clazz.getSimpleName() + "Test";

        String packageName = clazz.getPackage().getName();
        LINES.add("package " + packageName + ";");
        addImport();
        LINES.add("@ExtendWith(SpringExtension.class)");
        LINES.add("@SpringBootTest");
        LINES.add("@TestMethodOrder(MethodOrderer.OrderAnnotation.class)");
        LINES.add("@TestInstance(TestInstance.Lifecycle.PER_CLASS)");
        LINES.add("public class " + testClassName + "{");
        mockAutoWiredBean(clazz);
        addTestMethod(clazz);
        LINES.add("}");

        String packagePath = packageName.replaceAll("\\.", "/");
        String pathname = ROOT_PATH + PROJECT_NAME + SECONDARY_PATH + packagePath + "/" + testClassName + ".java";
        FileUtils.writeLines(new File(pathname), LINES);
        LINES.clear();
    }

    static void mockAutoWiredBean(Class<?> clazz) throws IOException {

        List<Field> fieldToMock = addMockBean(clazz);
        String controllerVariable = switchCaseOfFirstChar(clazz.getSimpleName());

        LINES.add("@BeforeAll");
        LINES.add("public void setup() {");
        LINES.add("mockMvc = MockMvcBuilders.standaloneSetup(" + controllerVariable + ").build();");
        String s = readTargetClassFile(clazz);

        Map<Field, Set<String>> maps = new HashMap<>(16);
        Set<String> returnSet = new HashSet<>();
        for (Field field : fieldToMock) {
            Pattern p = Pattern.compile(field.getName() + "\\.[\\d\\D]*?\\(");
            Matcher m = p.matcher(s);
            while (m.find()) {
                String group = m.group();
                String methodName = group.replace(field.getName() + ".", "").replace("(", "");
                maps.computeIfAbsent(field, i -> new HashSet<>()).add(methodName);
                doMockBean(returnSet, field, group);
            }
        }
        maps.forEach((field, v) -> v.forEach(methodName -> doMockBean(returnSet, field, methodName)));
        LINES.add("}");
    }

    private static void doMockBean(Set<String> returnSet, Field field, String methodName) {

        Method[] methods = field.getType().getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {

                String r = "null";
                Type genericReturnType = method.getGenericReturnType();
                if (genericReturnType instanceof Class) {
                    Class<?> genericReturnClass = (Class<?>) genericReturnType;
                    r = getReturnInstanceName(genericReturnClass, returnSet);
                } else if (genericReturnType instanceof ParameterizedTypeImpl) {
                    ParameterizedTypeImpl genericReturnClass = (ParameterizedTypeImpl) genericReturnType;
                    Class<?> rawType = genericReturnClass.getRawType();
                    String returnInstanceName = getReturnInstanceName(
                            ((Class<?>) genericReturnClass.getActualTypeArguments()[0]), returnSet);
                    if (rawType.equals(List.class)) {
                        r = "Collections.singletonList(" + returnInstanceName + ")";
                    } else if (rawType.equals(Set.class)) {
                        r = "Collections.singleton(" + returnInstanceName + ")";
                    }
                } else {
                    LINES.add("// todo init the return instance manually");
                }
                String collect = Arrays.stream(method.getParameters())
                                       .map(j -> "any()")
                                       .collect(Collectors.joining(", "));
                LINES.add("given(" + field.getName() + "." + methodName + "(" + collect + "))"
                        + ".willReturn" + "(" + r + ");");
            }
        }
    }

    private static String readTargetClassFile(Class<?> clazz) throws IOException {
        String packageName = clazz.getPackage().getName();
        String packagePath = packageName.replaceAll("\\.", "/");
        String pathname = ROOT_PATH + PROJECT_NAME + SECONDARY_PATH.replace("test", "main") +
                packagePath + "/" + clazz.getSimpleName() + ".java";
        return FileUtils.readFileToString(new File(pathname), "utf-8");
    }

    private static String getReturnInstanceName(Class<?> genericReturnClass, Set<String> set) {

        String instanceName = "a" + genericReturnClass.getSimpleName();
        String v = mockValue(genericReturnClass);
        if (!"null".equals(v)) {
            return v;
        } else if (!set.contains(instanceName)) {
            initInstance(genericReturnClass);
        }
        set.add(instanceName);
        return instanceName;
    }

    private static void addTestMethod(Class<?> aClass) {

        String basePath = getBasePath(aClass);
        for (Method declaredMethod : aClass.getDeclaredMethods()) {

            MappingResult mappingResult = parseMapping(declaredMethod);
            if (mappingResult.isMapping()) {

                LINES.add("@Test");
                LINES.add("public void " + declaredMethod.getName() + "() throws Exception{");
                addRequest(basePath, declaredMethod, mappingResult);
                LINES.add("        MvcResult result = mockMvc.perform(requestBuilder)\n" +
                        "                .andExpect(status().isOk())\n" +
                        "                .andReturn();");
//                addAssert(declaredMethod);
                LINES.add("}");
            }
        }

    }

    private static void addRequest(String basePath, Method declaredMethod, MappingResult mappingResult) {

        Parameter[] parameters = declaredMethod.getParameters();
        Optional<Parameter> first = Arrays.stream(parameters)
                                          .filter(i -> i.isAnnotationPresent(RequestBody.class))
                                          .findFirst();
        first.ifPresent(i -> initInstance(i.getType()));

        String requestMethod = StringUtils.lowerCase(mappingResult.getMethod().name());
        String pathStrBuilder = "MockHttpServletRequestBuilder requestBuilder =" + requestMethod +
                "(\"" + basePath + mappingResult.getValue() + "\"" + getPathVariable(parameters) + ")";
        LINES.add(pathStrBuilder);
        addRequestParam(parameters);

        first.ifPresent(i -> LINES.add(".content(new ObjectMapper().writeValueAsString(a"
                + i.getType().getSimpleName() + "))"));
        addContentType(mappingResult.getProduce());
    }

    private static void addContentType(String produce) {
        String json = "application/json";
        if (json.equals(produce) || produce == null) {
            produce = "MediaType.APPLICATION_JSON_VALUE";
            LINES.add(".contentType(" + produce + ");");
        } else {
            LINES.add(".contentType(\"" + produce + "\");");
        }

    }

    private static void initInstance(Class<?> type) {

        String fieldName = "a" + type.getSimpleName();
        LINES.add(type.getSimpleName() + " " + fieldName + " = new " + type.getSimpleName() + "();");
        for (Field declaredField : type.getDeclaredFields()) {
            LINES.add(fieldName + ".set" + switchCaseOfFirstChar(declaredField.getName()) + ("(" + mockValue(declaredField.getType()) + ");"));
        }
    }

    private static void addAssert(Method declaredMethod) {

        Type genericReturnType = declaredMethod.getGenericReturnType();
        String returnType = getReturnTypeStr(genericReturnType);

        LINES.add(returnType + " jsonEntity = new ObjectMapper().readValue(result.getResponse().getContentAsString(),");
        LINES.add("new TypeReference<" + returnType + ">() {");
        LINES.add("});");
        LINES.add("Assertions.assertEquals(jsonEntity.getStatus(), 200);");
    }

    static Pattern p1 = Pattern.compile("[<>,]+");
    static Pattern p = Pattern.compile("[^<>,]+");

    private static String getReturnTypeStr(Type genericReturnType) {
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
        return typeName;
    }

    private static void addRequestParam(Parameter[] parameters) {

        StringBuilder paramStrBuilder = new StringBuilder();
        Arrays.stream(parameters)
              .filter(i -> i.isAnnotationPresent(RequestParam.class))
              .forEach(paramParameter -> {

                  String v = mockValue(parameters.getClass());
                  String paramName = paramParameter.getAnnotation(RequestParam.class).value();
                  paramStrBuilder.append(".param(\"").append(paramName).append("\",\"").append(v).append("\")\n");
              });

        if (paramStrBuilder.length() > 0) {
            LINES.add(paramStrBuilder.toString());
        }
    }

    private static String getPathVariable(Parameter[] parameters) {

        String pathParams = Arrays.stream(parameters)
                                  .filter(i -> i.isAnnotationPresent(PathVariable.class))
                                  .map(Parameter::getType)
                                  .map(WebUTGenerator::mockValue).collect(Collectors.joining(", "));
        return pathParams.length() == 0 ? pathParams : ", " + pathParams;
    }

    private static String mockValue(Class<?> type) {
        String v = "null";
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

    private static List<Field> addMockBean(Class<?> clazz) {
        List<Field> fieldToMock = new ArrayList<>();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Autowired.class)) {
                fieldToMock.add(declaredField);
                LINES.add("@MockBean");
                LINES.add("private " + declaredField.getType().getSimpleName() + " " + declaredField.getName() + ";");
            }
        }
        LINES.add("@Autowired");
        String controllerVariable = switchCaseOfFirstChar(clazz.getSimpleName());
        LINES.add("private " + clazz.getSimpleName() + " " + controllerVariable + ";");
        LINES.add("");
        LINES.add("private MockMvc mockMvc;");

        return fieldToMock;
    }

    public static String switchCaseOfFirstChar(String str) {

        char[] cs = str.toCharArray();
        char a = 'a';
        if (cs[0] >= a) {
            cs[0] -= 32;
        } else {
            cs[0] += 32;
        }
        return String.valueOf(cs);
    }

    private static void addImport() {
        LINES.add("import static org.mockito.ArgumentMatchers.any;\n" +
                "import static org.mockito.BDDMockito.given;\n" +
                "import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;\n" +
                "import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;\n" +
                "import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;\n" +
                "import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;\n" +
                "import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;\n" +
                "import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;\n" +
                "import org.springframework.boot.test.mock.mockito.MockBean;\n" +
                "import org.springframework.test.web.servlet.MvcResult;\n" +
                "import org.springframework.boot.test.mock.mockito.MockBean;\n" +
                "import org.springframework.context.MessageSource;\n" +
                "import org.springframework.http.MediaType;\n" +
                "import org.junit.jupiter.api.Test;\n" +
                "import org.junit.jupiter.api.BeforeAll;\n" +
                "import org.junit.jupiter.api.extension.ExtendWith;\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import org.springframework.boot.test.context.SpringBootTest;\n" +
                "import org.springframework.test.context.junit.jupiter.SpringExtension;\n" +
                "import org.springframework.test.web.servlet.MvcResult;\n" +
                "import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;\n" +
                "import org.springframework.test.web.servlet.setup.MockMvcBuilders;\n" +
                "import com.fasterxml.jackson.databind.ObjectMapper;\n" +
                "import com.fasterxml.jackson.core.type.TypeReference;\n" +
                "import com.fasterxml.jackson.databind.ObjectMapper;\n" +
                "import org.mockito.MockedStatic;\n" +
                "import org.mockito.Mockito;\n" +
                "import com.fasterxml.jackson.databind.ObjectMapper;\n" +
                "import java.util.List;\n" +
                "import java.util.Map;\n" +
                "import java.util.Set;\n");
    }

    private static String getBasePath(Class<?> aClass) {
        RequestMapping requestMapping = aClass.getAnnotation(RequestMapping.class);
        String basePath = requestMapping.name();
        if (StringUtils.isBlank(basePath)) {
            basePath = requestMapping.value()[0];
        }
        return basePath;
    }

    public static MappingResult parseMapping(Method declaredMethod) {

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

}

