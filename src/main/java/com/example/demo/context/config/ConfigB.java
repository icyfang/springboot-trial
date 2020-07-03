package com.example.demo.context.config;

import com.example.demo.context.properties.BookProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(BeanBImportSelector.class)
public class ConfigB {


}
