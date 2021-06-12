package com.example.springcontext.aop.args;

import com.example.springcontext.aop.CustomAnn;
import org.aspectj.lang.JoinPoint;
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
public class ArgsAspect {

    @Pointcut("args(a,b)")
    private void argsPoint(String a, String b) {
    }

    // too generic poincut may cause unexpected proxy
    @Pointcut("execution(* com.example.springcontext.aop.args.*.*(..))")
    private void argsComponentPoint() {
    }

    @Pointcut("args(java.lang.String,java.lang.String)")
    private void argsPointByType() {
    }

    @Pointcut("@args(ann)")
    private void argsPointByAnn(CustomAnn ann) {
    }

    @Pointcut("@args(ann,..)")
    private void multiArgsPointByAnn(CustomAnn ann) {
    }

    @Before("args(a,b) && argsComponentPoint()")
    public void beforeArgs(JoinPoint j, String a, String b) {
        System.out.println("a: " + a);
        System.out.println("b: " + b);
        System.out.println("before args advice");
    }

    // can not inject parameter
    @Before("argsPointByType() && argsComponentPoint()")
    public void beforeArgsByType(JoinPoint j) {
        System.out.println("before argsByType advice");
    }

    @Before("argsPointByAnn(ann) && argsComponentPoint()")
    public void beforeArgsByAnn(JoinPoint j, CustomAnn ann) {
        System.out.println(ann.value());
        System.out.println("before argsByAnn advice");
    }

    @Before("multiArgsPointByAnn(ann) && argsComponentPoint()")
    public void beforeMultiArgsByAnn(JoinPoint j, CustomAnn ann) {
        System.out.println(ann.value());
        System.out.println("before multiArgsByAnn advice");
    }

}
