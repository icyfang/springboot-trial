package com.example.springcontext.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * @author Hodur
 * @date 2020/8/19
 */
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    public MyBeanFactoryPostProcessor() {
        super();
        System.out.println("BeanFactoryPostProcessor constructor");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("BeanFactoryPostProcessor#postProcessBeanFactory");
        BeanDefinition bd = beanFactory.getBeanDefinition("person");
        bd.getPropertyValues().addPropertyValue("name", "Tom");
    }
}
