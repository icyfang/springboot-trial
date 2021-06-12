package com.example.springcontext.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * @author Hodur
 * @date 2020-08-19
 */
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    public MyBeanFactoryPostProcessor() {
        super();
        System.out.println("BeanFactoryPostProcessor constructor");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory arg0) throws BeansException {
        System.out.println("BeanFactoryPostProcessor#postProcessBeanFactory");
        BeanDefinition bd = arg0.getBeanDefinition("person");
        bd.getPropertyValues().addPropertyValue("name", "张三");
    }
}
