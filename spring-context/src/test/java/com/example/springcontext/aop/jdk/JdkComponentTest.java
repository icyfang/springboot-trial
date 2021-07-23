package com.example.springcontext.aop.jdk;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class JdkComponentTest {

    @Autowired
    private JdkInterface jdkInterface;

    @Test
    void act() {
        jdkInterface.act(1, 2, 3, 4, 5, 6, 7, 8, 9, 0);
    }

    @Test
    void doAct() {
        jdkInterface.doAct(1, 2, 3, 4, 5, 6, 7, 8, 9, 0);
    }

    @Test
    void actProxy() {
        jdkInterface.actProxy(1, 2, 3, 4, 5, 6, 7, 8, 9, 0);
    }

    @Test
    void actException() {
        jdkInterface.act(1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1);
    }
}
