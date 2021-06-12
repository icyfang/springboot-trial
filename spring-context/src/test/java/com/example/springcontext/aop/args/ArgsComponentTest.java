package com.example.springcontext.aop.args;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ArgsComponentTest {

    @Autowired
    private ArgsComponent component;

    @Test
    void act() {
        component.act("a", "b");
    }

    @Test
    void testAct() {
        AnnotatedClass a = new AnnotatedClass();
        a.setName("a");
        component.act(a, 2);
    }

    @Test
    void testAct1() {
        AnnotatedClass a = new AnnotatedClass();
        a.setName("a");
        component.act(a);
    }
}
