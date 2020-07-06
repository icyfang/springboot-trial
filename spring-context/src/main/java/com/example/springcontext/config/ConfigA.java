package com.example.springcontext.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(BeanABeanDefinitionRegistrar.class)
public class ConfigA {
}
