package com.example.springcontext.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.lang.NonNull;

/**
 * @author Hodur
 * @date 2020-08-19
 */
public class MyInstantiationAwareBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {
    public MyInstantiationAwareBeanPostProcessor() {
        super();
        System.out.println("InstantiationAwareBeanPostProcessorAdapter constructor");
    }

    private final static String TARGET_BEAN_NAME = "person";

    /**
     * 接口方法、实例化Bean之前调用
     */
    @Override
    public Object postProcessBeforeInstantiation(@NonNull Class beanClass, @NonNull String beanName) throws BeansException {
        if (TARGET_BEAN_NAME.equals(beanName)) {
            System.out.println("InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation");
        }
        return null;
    }

    /**
     * 接口方法、实例化Bean之后调用
     */
    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if (TARGET_BEAN_NAME.equals(beanName)) {
            System.out.println("InstantiationAwareBeanPostProcessor#postProcessAfterInitialization");
        }
        return bean;
    }

    /**
     * 接口方法、设置某个属性时调用
     */
    @Override
    public PropertyValues postProcessProperties(@NonNull PropertyValues pvs, @NonNull Object bean,
                                                @NonNull String beanName)
            throws BeansException {
        if (TARGET_BEAN_NAME.equals(beanName)) {
            System.out.println("InstantiationAwareBeanPostProcessor#postProcessPropertyValues");
        }
        return pvs;
    }
}
