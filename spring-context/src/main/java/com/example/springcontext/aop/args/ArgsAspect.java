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

    @Pointcut(value = "args(a,b)", argNames = "a,b")
    private void argsPoint(String a, String b) {
    }

    /**
     * too generic pointcut may cause unexpected proxy
     */
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

    @Before(value = "args(a,b) && argsComponentPoint()", argNames = "j,a,b")
    public void beforeArgs(JoinPoint j, String a, String b) {
        System.out.println("a: " + a);
        System.out.println("b: " + b);
        System.out.println("before args advice");
    }

    /**
     * can not inject parameter
     *
     * @param j j
     */
    @Before("argsPointByType() && argsComponentPoint()")
    public void beforeArgsByType(JoinPoint j) {
        System.out.println("before argsByType advice");
    }

    @Before(value = "argsPointByAnn(ann) && argsComponentPoint()", argNames = "j,ann")
    public void beforeArgsByAnn(JoinPoint j, CustomAnn ann) {
        System.out.println(ann.value());
        System.out.println("before argsByAnn advice");
    }

    @Before(value = "multiArgsPointByAnn(ann) && argsComponentPoint()", argNames = "j,ann")
    public void beforeMultiArgsByAnn(JoinPoint j, CustomAnn ann) {
        System.out.println(ann.value());
        System.out.println("before multiArgsByAnn advice");
    }

}
