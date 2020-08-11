package com.example.springcontext.config;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;


public class BeanABeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry registry) {

        BeanDefinitionBuilder beanA = BeanDefinitionBuilder.rootBeanDefinition(BeanA.class);
        //通过registry就可以注入到容器里啦

        registry.registerBeanDefinition("beanA", beanA.getBeanDefinition());
    }
}
