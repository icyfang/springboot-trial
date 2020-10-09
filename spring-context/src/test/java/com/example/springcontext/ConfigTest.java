package com.example.springcontext;

import com.example.springcontext.config.BeanA;
import com.example.springcontext.config.BeanB;
import com.example.springcontext.config.ConfigB;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigTest {

    @Test
    public void test() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        BeanA userService = context.getBean(BeanA.class);
        userService.save();
        context.close();
    }

    @Test
    public void test1() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigB.class);
        BeanB beanB = context.getBean(BeanB.class);
        beanB.run();
        context.close();
    }
}
