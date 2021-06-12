package com.example.springcontext.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

import java.beans.PropertyDescriptor;

/**
 * @author Hodur
 * @date 2020-08-19
 */
public class MyInstantiationAwareBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {
    public MyInstantiationAwareBeanPostProcessor() {
        super();
        System.out.println("InstantiationAwareBeanPostProcessorAdapter constructor");
    }

    /**
     * 接口方法、实例化Bean之前调用
     */
    @Override
    public Object postProcessBeforeInstantiation(Class beanClass, String beanName) throws BeansException {
        if (beanName.equals("person")) {
            System.out.println("InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation");
        }
        return null;
    }

    /**
     * 接口方法、实例化Bean之后调用
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("person")) {
            System.out.println("InstantiationAwareBeanPostProcessor#postProcessAfterInitialization");
        }
        return bean;
    }

    /**
     * 接口方法、设置某个属性时调用
     */
    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs,
                                                    PropertyDescriptor[] pds, Object bean, String beanName)
            throws BeansException {
        if (beanName.equals("person")) {
            System.out.println("InstantiationAwareBeanPostProcessor#postProcessPropertyValues");
        }
        return pvs;
    }
}
