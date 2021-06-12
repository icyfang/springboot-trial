
package com.example.springcontext.aop.cglib;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author Hodur
 * @since 2021-05-23
 */
@Component
@Aspect
public class CglibAspect {

    @Before("bean(cglibComponent)")
    private void beforeAdvice(JoinPoint j) {

        System.out.println("before advice");

        System.out.println("");
        Arrays.stream(j.getArgs()).forEach(System.out::println);
        System.out.println(j.getKind());
        System.out.println(j.toLongString());
        System.out.println(j.toShortString());
        System.out.println(j.toString());
        System.out.println(j.getTarget().getClass());
        System.out.println(j.getThis().getClass());

        Signature signature = j.getSignature();
        System.out.println("");
        System.out.println(signature.getDeclaringTypeName());
        System.out.println(signature.getModifiers());
        System.out.println(signature.getName());
        System.out.println(signature.toLongString());
        System.out.println(signature.toShortString());
        System.out.println(signature.toString());
        System.out.println(signature.getDeclaringType());

        MethodSignature methodSignature = (MethodSignature) signature;
        System.out.println(methodSignature.getReturnType());
        System.out.println(methodSignature.getMethod());
        CodeSignature codeSignature = (CodeSignature) signature;
        Arrays.stream(codeSignature.getExceptionTypes()).forEach(System.out::println);
        Arrays.stream(codeSignature.getParameterNames()).forEach(System.out::println);
        Arrays.stream(codeSignature.getParameterTypes()).forEach(System.out::println);

        System.out.println("");
        SourceLocation sourceLocation = j.getSourceLocation();
        // not supported
//        System.out.println(sourceLocation.getFileName());
//        System.out.println(sourceLocation.getLine());
        System.out.println(sourceLocation.getWithinType());

        System.out.println("");
        JoinPoint.StaticPart staticPart = j.getStaticPart();
        System.out.println(staticPart.getId());
        System.out.println(staticPart.getSignature().getDeclaringTypeName());
        System.out.println(staticPart.getKind());
        System.out.println(staticPart.toString());

    }

    @After("bean(cglibComponent)")
    private void afterAdvice(JoinPoint j) {
        System.out.println("after advice");
    }

    @AfterReturning(value = "bean(cglibComponent)", returning = "i")
    private void afterReturningAdvice(JoinPoint j, Object i) {
        System.out.println("after return advice");
        System.out.println(i);
    }

    @AfterThrowing(value = "bean(cglibComponent)", throwing = "ex")
    private void afterThrowingAdvice(JoinPoint j, Throwable ex) {
        System.out.println("after throwing advice");
        System.out.println(ex.getMessage());
    }

    @Around("bean(cglibComponent)")
    private Integer aroundAdvice(ProceedingJoinPoint j) throws Throwable {

        System.out.println("around service");
        try {
            Object proceed = j.proceed();
            System.out.println(proceed);
            return (Integer) proceed;
        } catch (Throwable throwable) {
            System.out.println(throwable.getMessage());
            throw throwable;
        }
    }

}
