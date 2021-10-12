package com.example.springcontext.aop.order;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;

/**
 * @author Hodur
 * @date 2021/10/12
 */
@Aspect
public class AspectTwo implements Ordered {
    /**
     * Pointcut定义切点函数
     */
    @Pointcut("execution(* com.example.springcontext.aop.order.OrderComponent.*(..))")
    private void myPointcut() {
    }

    @Before("myPointcut()")
    public void beforeOne() {
        System.out.println("前置通知....执行顺序1--AspectTwo");
    }

    @Before("myPointcut()")
    public void beforeTwo() {
        System.out.println("前置通知....执行顺序2--AspectTwo");
    }

    @AfterReturning(value = "myPointcut()")
    public void afterReturningThree() {
        System.out.println("后置通知....执行顺序3--AspectTwo");
    }

    @AfterReturning(value = "myPointcut()")
    public void afterReturningFour() {
        System.out.println("后置通知....执行顺序4--AspectTwo");
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
