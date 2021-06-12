package com.example.springcontext.aop.cglib;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CglibComponentTest {

    @Autowired
    private CglibComponent component;

    @Test
    void act() {
        component.act(1, 2, 3, 4, 5, 6, 7, 8, 9, 0);
    }

    @Test
    void doAct() {
        component.doAct(1, 2, 3, 4, 5, 6, 7, 8, 9, 0);
    }

    @Test
    void actException() {
        component.act(1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1);
    }
}
