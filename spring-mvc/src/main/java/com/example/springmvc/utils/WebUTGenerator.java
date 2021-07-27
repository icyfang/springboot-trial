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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Generator of controller ut template
 *
 * @author Hodur
 * @date 2021/7/27
 */
public class WebUTGenerator {

    // project name
    static String projectName = "spring-mvc";
    // absolute path of project
    static final String rootPath = "D:\\IdeaProjects\\";
    static final String secondaryPath = "\\web\\src\\test\\java\\";

    public static void main(String[] args) throws IOException {

        genUT(UserController.class);
    }

    public static void genUT(Class<?> clazz) throws IOException {

        Controller annotation = clazz.getAnnotation(Controller.class);
        if (annotation == null && clazz.getAnnotation(RestController.class) == null) {
            throw new RuntimeException("not a controller");
        }
        String testClassName = clazz.getSimpleName() + "Test1";
        List<String> lines = new ArrayList<>();

        String packageName = clazz.getPackage().getName();
        lines.add("package " + packageName + ";");
        addImport(lines);
        lines.add("@ExtendWith(SpringExtension.class)");
        lines.add("@SpringBootTest");
        lines.add("public class " + testClassName + "{");
        addMockBean(clazz, lines);
        addMockMvc(clazz, lines);
        addTestMethod(clazz, lines);

        String packagePath = packageName.replaceAll("\\.", "/");
        String pathname = rootPath + projectName + secondaryPath + packagePath + "/" + testClassName + ".java";

        FileUtils.writeLines(new File(pathname), lines);
    }

    private static void addMockMvc(Class<?> clazz, List<String> lines) {
        lines.add("@Autowired");
        String controllerVariable = switchCaseOfFirstChar(clazz.getSimpleName());
        lines.add("private " + clazz.getSimpleName() + " " + controllerVariable + ";");
        lines.add("");
        lines.add("private MockMvc mockMvc;");
        lines.add("@BeforeAll");
        lines.add("public void setup() {");
        lines.add("mockMvc = MockMvcBuilders.standaloneSetup(" + controllerVariable + ").build();");
        lines.add("}");
    }

    public static String switchCaseOfFirstChar(String str) {

        char[] cs = str.toCharArray();
        if (cs[0] > 90) {
            cs[0] -= 32;
        } else {
            cs[0] += 32;
        }
        return String.valueOf(cs);
    }

    private static void addTestMethod(Class<?> aClass, List<String> lines) {

        String basePath = getBasePath(aClass);
        Method[] declaredMethods = aClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {

            MappingResult mappingResult = parseMapping(declaredMethod);
            String produce = mappingResult.getProduce();
            if (mappingResult.isMapping()) {

                Parameter[] parameters = declaredMethod.getParameters();

                lines.add("@Test");
                lines.add("public void " + declaredMethod.getName() + "() throws Exception{");

                Optional<Parameter> first = Arrays.stream(parameters)
                                                  .filter(i -> i.isAnnotationPresent(RequestBody.class))
                                                  .findFirst();
                first.ifPresent(i -> {
                    Class<?> type = i.getType();
                    lines.add(type.getSimpleName() + " body = new " + type.getSimpleName() + "();");
                    Field[] declaredFields = type.getDeclaredFields();
                    for (Field declaredField : declaredFields) {
                        if (declaredField.isAnnotationPresent(NotNull.class)
                                || declaredField.isAnnotationPresent(NotBlank.class)) {
                            Class<?> field = declaredField.getType();
                            if (field.equals(Integer.class) || field.equals(Long.class)) {
                                lines.add("body.set" + switchCaseOfFirstChar(declaredField.getName()) + "(1);");
                            } else if (field.equals(String.class)) {
                                lines.add("body.set" + switchCaseOfFirstChar(declaredField.getName()) + "(\"str\");");
                            }
                        }
                    }
                });

                StringBuilder pathStrBuilder = new StringBuilder();
                String requestMethod = StringUtils.lowerCase(mappingResult.getMethod().name());
                pathStrBuilder.append("MvcResult result = mockMvc.perform(").append(requestMethod).append("(\"");
                pathStrBuilder.append(basePath).append(mappingResult.getValue()).append("\"");
                pathStrBuilder.append(getPathVariable(parameters));
                pathStrBuilder.append(")");
                lines.add(pathStrBuilder.toString());

                lines.add(getRequestParam(parameters));

                first.ifPresent(i -> lines.add(".content(new ObjectMapper().writeValueAsString(body))"));

                if ("application/json".equals(produce) || produce == null) {
                    produce = "MediaType.APPLICATION_JSON_VALUE";
                    lines.add(".contentType(" + produce + "))");
                } else {
                    lines.add(".contentType(\"" + produce + "\"))");
                }
                lines.add(".andExpect(status().isOk())");
                lines.add(".andReturn();");

                lines.add("}");
            }
        }

        lines.add("}");
    }

    private static String getRequestParam(Parameter[] parameters) {

        StringBuilder paramStrBuilder = new StringBuilder();
        Arrays.stream(parameters)
              .filter(i -> i.isAnnotationPresent(RequestParam.class))
              .forEach(paramParameter -> {

                  String v = "";
                  if (paramParameter.getType().equals(Integer.class) || paramParameter.getType().equals(Long.class)) {
                      v = "1";
                  }
                  if (paramParameter.getType().equals(String.class)) {
                      v = "str";
                  }
                  String paramName = paramParameter.getAnnotation(RequestParam.class).value();
                  paramStrBuilder.append(".param(\"").append(paramName).append("\",\"").append(v).append("\")\n");
              });

        return paramStrBuilder.toString();
    }

    private static String getPathVariable(Parameter[] parameters) {

        StringBuilder pathStrBuilder = new StringBuilder();
        Arrays.stream(parameters)
              .filter(i -> i.isAnnotationPresent(PathVariable.class))
              .forEach(pathVariable -> {
                  if (pathVariable.getType().equals(Integer.class) || pathVariable.getType().equals(Long.class)) {
                      pathStrBuilder.append(",").append("1");
                  }
                  if (pathVariable.getType().equals(String.class)) {
                      pathStrBuilder.append(",").append("\"").append("str").append("\"");
                  }
              });
        return pathStrBuilder.toString();
    }

    private static void addMockBean(Class<?> aClass, List<String> lines) {
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Autowired.class)) {
                lines.add("@MockBean");
                lines.add("private " + declaredField.getType().getSimpleName() + " " + declaredField.getName() + ";");
            }
        }
    }

    private static void addImport(List<String> lines) {
        lines.add("import static org.mockito.ArgumentMatchers.any;\n" +
                "import static org.mockito.BDDMockito.given;\n" +
                "import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;\n" +
                "import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;\n" +
                "import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;\n" +
                "import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;\n" +
                "import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;\n" +
                "import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;\n" +
                "import org.springframework.boot.test.context.SpringBootTest;\n" +
                "import org.springframework.boot.test.mock.mockito.MockBean;\n" +
                "import org.springframework.test.web.servlet.MvcResult;\n" +
                "import org.springframework.context.MessageSource;\n" +
                "import org.springframework.http.MediaType;\n" +
                "import org.junit.jupiter.api.BeforeAll;\n" +
                "import org.junit.jupiter.api.Test;\n" +
                "import org.junit.jupiter.api.extension.ExtendWith;\n" +
                "import com.fasterxml.jackson.core.type.TypeReference;\n" +
                "import org.mockito.MockedStatic;\n" +
                "import org.mockito.Mockito;\n" +
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
            produce = mapping.produces()[0];
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