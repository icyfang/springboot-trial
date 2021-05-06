package com.example.springcontext.lifecycle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Hodur
 * @date 2020-08-19
 */
@Configuration
public class PersonConfig {

    @Bean(initMethod = "myInit", destroyMethod = "myDestory")
    public Person person() {

        Person person = new Person();
        person.setName("name");
        return person;
    }

    @Bean
    public MyBeanFactoryPostProcessor myBeanFactoryPostProcessor() {
        return new MyBeanFactoryPostProcessor();
    }

    @Bean
    public MyBeanPostProcessor myBeanPostProcessor() {
        return new MyBeanPostProcessor();
    }

    @Bean
    public MyInstantiationAwareBeanPostProcessor myInstantiationAwareBeanPostProcessor() {
        return new MyInstantiationAwareBeanPostProcessor();
    }
}
