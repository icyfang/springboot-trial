package com.example.springcontext.aop.within;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author Hodur
 * @since 2021-05-23
 */
@Component
@Aspect
public class WithinAspect {

    @Pointcut(value = "within(com.example.springcontext.aop.within.WithinComponent)")
    public void componentPoint() {
    }

    @Pointcut(value = "within(@org.springframework.stereotype.Service com.example.springcontext.aop.within.*)")
    public void servicePoint() {
    }

    @Pointcut(value = "@within(org.springframework.stereotype.Component)")
    public void componentAnnotationPoint() {
    }

    /**
     * not work for method annotation
     */
    @Pointcut(value = "@within(com.example.springcontext.aop.CustomAnn)")
    public void customAnnotationPoint() {
    }

    @Before(value = "componentPoint()")
    public void beforeComponent(JoinPoint p) {
        System.out.println("before Component");
    }

    @Before(value = "servicePoint()")
    public void beforeService(JoinPoint p) {
        System.out.println("before Service");
    }

    @Before(value = "componentPoint()&& componentAnnotationPoint()")
    public void beforeComponentAnnotation(JoinPoint p) {
        System.out.println("before Component Annotation");
    }

    @Before(value = "componentPoint()&& customAnnotationPoint()")
    public void beforeCustomAnnotation(JoinPoint p) {
        System.out.println("before Custom Annotation");
    }
}
