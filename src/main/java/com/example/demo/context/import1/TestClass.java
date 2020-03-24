package com.example.demo.context.import1;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestClass {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        BeanA userService = context.getBean(BeanA.class);
        userService.save();
        AnnotationConfigApplicationContext context1 = new AnnotationConfigApplicationContext(ConfigB.class);
        BeanB beanB = context1.getBean(BeanB.class);
        beanB.run();
        context.close();
    }
}
