package com.example.springredis.service;

import com.example.basic.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CallbackImplTest {

    @Autowired
    private CallbackImpl callback;

    @Test
    @Order(1)
    void insert() {

        callback.insert(new User(1L, "user1", 20));
    }

    @Test
    @Order(2)
    void get() {
        User user1 = callback.get("user1");
        Assertions.assertEquals(user1, new User(1L, "user1", 20));
    }
}
