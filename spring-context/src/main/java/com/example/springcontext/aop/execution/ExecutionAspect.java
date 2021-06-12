package com.example.springcontext.aop.execution;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author Hodur
 * @since 2021-05-26
 */
@Component
@Aspect
public class ExecutionAspect {

    @Pointcut("execution(* *(@com.example.springcontext.aop.CustomAnn (*)))")
    private void annPoint() {
    }

    @Before("annPoint()")
    public void beforeAnnPoint(JoinPoint j) {
        System.out.println("before ann point");
    }

    @Before("execution(* *(.., @com.example.springcontext.aop.CustomAnn (*), ..))")
    public void beforeMultiAnnPoint(JoinPoint j) {
        System.out.println("before multiAnn point");
    }
}
