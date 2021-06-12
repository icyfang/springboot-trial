package com.example.springcontext.aop.target;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author Hodur
 * @since 2021-05-25
 */
@Component
@Aspect
public class TargetAspect {

    @Pointcut("target(com.example.springcontext.aop.target.TargetInterfaceImpl)")
    public void targetPoint() {

    }

    @Before("targetPoint()")
    public void beforeAdvice() {
        System.out.println("before advice");
    }
}
