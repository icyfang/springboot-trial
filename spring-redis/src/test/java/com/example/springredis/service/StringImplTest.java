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
class StringImplTest {

    @Autowired
    private StringImpl stringImpl;

    @Test
    @Order(1)
    void insert() {
        stringImpl.insert(new User((long) 1, "user1", 20));
    }

    @Test
    @Order(2)
    void get() {
        User user1 = stringImpl.get("user1");
        Assertions.assertEquals(user1, new User((long) 1, "user1", 20));
    }

    @Order(3)
    @Test
    void update() {
        stringImpl.update("user1", new User((long) 1, "user1", 30));
        User user1 = stringImpl.get("user1");
        Assertions.assertEquals(user1, new User((long) 1, "user1", 30));
    }

    @Order(4)
    @Test
    void delete() {
        stringImpl.delete("user1");
        User user1 = stringImpl.get("user1");
        Assertions.assertNull(user1);
    }

    @Test
    @Order(5)
    void insertByBound() {
        stringImpl.insertByBound(new User((long) 1, "user1", 20));
    }

    @Test
    @Order(6)
    void getByBound() {
        User user1 = stringImpl.getByBound("user1");
        Assertions.assertEquals(user1, new User((long) 1, "user1", 20));
    }

    @Order(7)
    @Test
    void updateByBound() {
        stringImpl.updateByBound("user1", new User((long) 1, "user1", 30));
        User user1 = stringImpl.getByBound("user1");
        Assertions.assertEquals(user1, new User((long) 1, "user1", 30));
    }

    @Order(8)
    @Test
    void deleteByBound() {
        stringImpl.deleteByBound("user1");
        User user1 = stringImpl.getByBound("user1");
        Assertions.assertNull(user1);
    }

}
