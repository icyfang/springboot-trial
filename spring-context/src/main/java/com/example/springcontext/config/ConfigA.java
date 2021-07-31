package com.example.springcontext.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Hodur
 * @since 2021-05-28
 */
@Configuration
@Import(BeanABeanDefinitionRegistrar.class)
public class ConfigA {
}
