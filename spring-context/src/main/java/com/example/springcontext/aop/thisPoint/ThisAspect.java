package com.example.springcontext.aop.thisPoint;

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
public class ThisAspect {

    @Pointcut("this(com.example.springcontext.aop.thisPoint.ThisInterfaceImpl)")
    public void thisPoint() {

    }

    @Before("thisPoint()")
    public void beforeAdvice() {
        System.out.println("before advice");
    }
}
