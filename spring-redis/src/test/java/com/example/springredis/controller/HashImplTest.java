package com.example.springredis.controller;

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
class HashImplTest {

    @Autowired
    private HashImpl hashImpl;

    @Test
    @Order(1)
    void insert() {
        hashImpl.insert(new User((long) 1, "user1", 20));
    }

    @Test
    @Order(2)
    void get() {
        User user1 = hashImpl.get("user1");
        Assertions.assertEquals(user1, new User((long) 1, "user1", 20));
    }

    @Order(3)
    @Test
    void update() {
        hashImpl.update("user1", new User((long) 1, "user1", 30));
        User user1 = hashImpl.get("user1");
        Assertions.assertEquals(user1, new User((long) 1, "user1", 30));
    }

    @Order(4)
    @Test
    void delete() {
        hashImpl.delete("user1");
        User user1 = hashImpl.get("user1");
        Assertions.assertEquals(user1, new User());
    }

}
