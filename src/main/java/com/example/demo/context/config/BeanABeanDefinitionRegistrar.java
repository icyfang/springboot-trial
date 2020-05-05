package com.example.demo.context.config;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;


public class BeanABeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry registry) {

        BeanDefinitionBuilder BeanA = BeanDefinitionBuilder.rootBeanDefinition(BeanA.class);
        //通过registry就可以注入到容器里啦

        registry.registerBeanDefinition("BeanA", BeanA.getBeanDefinition());
    }
}