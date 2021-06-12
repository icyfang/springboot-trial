
package com.example.springcontext.aop.jdk;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author Hodur
 * @since 2021-05-23
 */
@Component
@Aspect
public class JdkAspect {

    @Around("bean(jdkComponent)")
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
