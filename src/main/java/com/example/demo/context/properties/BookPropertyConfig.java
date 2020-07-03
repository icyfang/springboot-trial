package com.example.demo.context.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookPropertyConfig {

    @Bean
    @ConfigurationProperties("mark")
    BookProperty bookProperty2() {
        return new BookProperty();
    }
}
