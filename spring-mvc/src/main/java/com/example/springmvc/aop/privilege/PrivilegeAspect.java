package com.example.springmvc.aop.privilege;

import lombok.Data;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shanghong Cai
 * @create 2020-07-20 21:38
 */
@Data
public class PrivilegeAspect {

    private List<Privilege> privileges = new ArrayList<>();

    public Object controlTargetMethod(ProceedingJoinPoint joinPoint) throws Throwable {

        Class targetClass = joinPoint.getTarget()
                                     .getClass();
        String methodName = joinPoint.getSignature()
                                     .getName();
        String privilegeName = AnnotationParser.parse(targetClass, methodName);
        boolean flag = false;
        for (Privilege privilege : this.privileges) {
            if (privilege.getName()
                         .equals(privilegeName)) {
                flag = true;
                break;
            }
        }
        if (flag) {
            return joinPoint.proceed();
        } else {
            return "access denied";
        }
    }
}
