package com.example.springcontext;

import com.example.springcontext.config.BeanA;
import com.example.springcontext.config.BeanB;
import com.example.springcontext.config.ConfigB;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigTest {

    @Test
    public void getBeanA() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        BeanA beanA = context.getBean(BeanA.class);
        beanA.save();
        context.close();
    }

    @Test
    public void getBeanB() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigB.class);
        BeanB beanB = context.getBean(BeanB.class);
        beanB.run();
        context.close();
    }
}
