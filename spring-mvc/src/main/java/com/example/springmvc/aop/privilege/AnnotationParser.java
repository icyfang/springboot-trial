
package com.example.springmvc.aop.privilege;

import java.lang.reflect.Method;

/**
 * @author Hodur
 * @create 2020-07-20 21:36
 */
public class AnnotationParser {
    public static String parse(Class<?> clazz, String methodName) throws Exception {
        String privilegeName = "";
        //目标方法
        Method method = clazz.getMethod(methodName);
        //判断目标方法上是否有PrivilegeInfo
        if (method.isAnnotationPresent(PrivilegeInfo.class)) {
            //获取目标方法上面的PrivilegeInfo注解
            PrivilegeInfo privilegeInfo = method.getAnnotation(PrivilegeInfo.class);
            //获取该注解的name属性的值
            privilegeName = privilegeInfo.name();
        }
        return privilegeName;
    }
}
