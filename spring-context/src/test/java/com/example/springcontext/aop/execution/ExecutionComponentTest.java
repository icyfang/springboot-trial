package com.example.springcontext.aop.execution;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ExecutionComponentTest {

    @Autowired
    private ExecutionComponent component;

    @Test
    void act() {
        component.act();
    }

    @Test
    void testAct() {
        component.act("s");
    }
}
