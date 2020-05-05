package com.example.demo.context.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(BeanBImportSelector.class)
public class ConfigB {
}
