package com.example.springcontext.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.lang.NonNull;

/**
 * @author Hodur
 * @date 2020/8/19
 */
public class MyBeanPostProcessor implements BeanPostProcessor, Ordered {
    public MyBeanPostProcessor() {
        super();
        System.out.println("BeanPostProcessor constructor");
    }

    private final static String TARGET_BEAN_NAME = "person";

    @Override
    public Object postProcessBeforeInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if (TARGET_BEAN_NAME.equals(beanName)) {
            System.out.println("BeanPostProcessor#postProcessBeforeInitialization");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if (TARGET_BEAN_NAME.equals(beanName)) {
            System.out.println("BeanPostProcessor#postProcessAfterInitialization");
            Person personBean = (Person) bean;
            personBean.setName(personBean.getName() + "_AfterBeanPostProcessor");
        }
        return bean;
    }

    @Override
    public int getOrder() {
        return 9;
    }
}
