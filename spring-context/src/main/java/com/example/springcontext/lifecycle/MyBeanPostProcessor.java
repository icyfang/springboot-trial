package com.example.springcontext.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author Hodur
 * @date 2020-08-19
 */
public class MyBeanPostProcessor implements BeanPostProcessor {
    public MyBeanPostProcessor() {
        super();
        System.out.println("BeanPostProcessor constructor");
    }

    @Override
    public Object postProcessAfterInitialization(Object arg0, String arg1) throws BeansException {
        if (arg1.equals("person")) {
            System.out.println("BeanPostProcessor#postProcessAfterInitialization");
        }
        return arg0;
    }

    @Override
    public Object postProcessBeforeInitialization(Object arg0, String arg1) throws BeansException {
        if (arg1.equals("person")) {
            System.out.println("BeanPostProcessor#postProcessBeforeInitialization");
        }
        return arg0;
    }
}
