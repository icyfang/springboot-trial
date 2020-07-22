package com.example.springmvc.exception;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author: Shanghong Cai
 * @descirption: aspect to handle MyException
 * @create: 2020-07-20 17:14
 */
public class MyExceptionAspect {

    public Object handleEx(ProceedingJoinPoint joinPoint) {
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
        } catch (Exception e) {
            System.out.println("exception occurs");
            return "exception occurs";
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return proceed;
    }
}