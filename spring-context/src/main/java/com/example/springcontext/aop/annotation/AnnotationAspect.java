package com.example.springcontext.aop.annotation;

import com.example.springcontext.aop.CustomAnn;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Hodur
 * @since 2021-05-25
 */
@Component
@Aspect
@Order
public class AnnotationAspect {

    @Pointcut(value = "@annotation(ann)&& bean(annotationComponent)")
    public void customAnnPoint(CustomAnn ann) {
    }

    @Before("customAnnPoint(ann)")
    public void beforeAdvice(JoinPoint j, CustomAnn ann) {
        System.out.println("AnnotationAspect before advice");
        System.out.println(ann.value());
    }
}
