package com.example.springmvc.jsr303;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validator;

/**
 * @author Hodur
 * @since 2020-11-02
 */
@Configuration
public class ValidationConfig {

    @Autowired
    private MessageSource messageSource;

    @Bean
    public Validator validator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

        validator.setValidationMessageSource(messageSource);
        validator.getValidationPropertyMap().put("hibernate.validator.fail_fast", "true");
        return validator;
    }

//    @Bean
//    public Validator validator() {
//        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
//                .configure()
//                // 设置validator模式为快速失败（只要有一个校验不通过就不立即返回错误）
//                .failFast(false)
//                .buildValidatorFactory();
//        return validatorFactory.getValidator();
//    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
        // 设置validator模式为快速失败返回
        postProcessor.setValidator(validator());
        return postProcessor;
    }

}