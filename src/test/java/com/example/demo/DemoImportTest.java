package com.example.demo;

import com.example.demo.context.import1.BeanA;
import com.example.demo.context.import1.BeanB;
import com.example.demo.context.import1.ConfigB;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DemoImportTest {

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
