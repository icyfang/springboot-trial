package com.example.springcontext.aop.annotation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AnnotationComponentTest {

    @Autowired
    private AnnotationComponent component;

    @Test
    void act() {
        component.act();
    }

    @Test
    void doAct() {
        component.doAct();
    }
}
