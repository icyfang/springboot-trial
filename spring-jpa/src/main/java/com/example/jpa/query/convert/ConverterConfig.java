package com.example.jpa.query.convert;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.convert.support.GenericConversionService;

import javax.annotation.PostConstruct;

@Configuration
public class ConverterConfig {

    @PostConstruct
    public void addConverter() {
        GenericConversionService genericConversionService = ((GenericConversionService) DefaultConversionService
                .getSharedInstance());
        genericConversionService.addConverter(new NativeConverter());
    }
}
