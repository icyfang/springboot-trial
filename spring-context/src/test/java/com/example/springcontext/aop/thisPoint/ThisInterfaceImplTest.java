package com.example.springcontext.aop.thisPoint;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ThisInterfaceImplTest {

    @Autowired
    private ThisInterface thisInterface;

    @Test
    void act() {
        thisInterface.act();
    }

    @Test
    void doAct() {
        thisInterface.doAct();
    }
}
