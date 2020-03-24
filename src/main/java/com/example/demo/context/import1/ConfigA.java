package com.example.demo.context.import1;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(BeanABeanDefinitionRegistrar.class)
public class ConfigA {
}
