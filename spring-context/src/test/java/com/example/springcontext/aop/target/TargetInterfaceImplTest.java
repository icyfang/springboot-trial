package com.example.springcontext.aop.target;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TargetInterfaceImplTest {

    @Autowired
    private TargetInterface targetInterface;

    @Test
    void act() {
        targetInterface.act();
    }

    @Test
    void doAct() {
        targetInterface.doAct();
    }
}
