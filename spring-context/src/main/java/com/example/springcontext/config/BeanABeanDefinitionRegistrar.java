package com.example.springcontext.config;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;

/**
 * @author Hodur
 * @since 2021-05-28
 */
public class BeanABeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(@NonNull AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry registry) {

        BeanDefinitionBuilder beanA = BeanDefinitionBuilder.rootBeanDefinition(BeanA.class);
        //通过registry就可以注入到容器里啦

        registry.registerBeanDefinition("beanA", beanA.getBeanDefinition());
    }
}
