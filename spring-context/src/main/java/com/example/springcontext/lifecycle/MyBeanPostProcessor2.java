package com.example.springcontext.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.lang.NonNull;

/**
 * @author Hodur
 * @date 2020/08/19
 */
public class MyBeanPostProcessor2 implements BeanPostProcessor, Ordered {

    public MyBeanPostProcessor2() {
        super();
        System.out.println("BeanPostProcessor2 constructor");
    }

    private final static String TARGET_BEAN_NAME = "person";

    @Override
    public Object postProcessBeforeInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if (TARGET_BEAN_NAME.equals(beanName)) {
            System.out.println("BeanPostProcessor2#postProcessBeforeInitialization");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if (TARGET_BEAN_NAME.equals(beanName)) {
            System.out.println("BeanPostProcessor2#postProcessAfterInitialization");
        }
        return bean;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
