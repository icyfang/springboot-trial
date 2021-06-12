package com.example.springcontext.aop.within;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class WithinServiceTest {

    @Autowired
    private WithinService service;

    @Autowired
    private WithinComponent component;

    @Test
    void actService() {
        service.act();
    }

    @Test
    void actComponent() {
        component.act();
    }

    @Test
    void actAnnotation() {
        component.act();
    }
}
