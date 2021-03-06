package com.example.springredis.controller;

import com.example.springredis.model.User;
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
class StringBeanTest {

    @Autowired
    private StringBean stringBean;

    @Test
    @Order(1)
    void insert() {
        stringBean.insert(new User("user1", "name1"));
    }

    @Test
    @Order(2)
    void get() {
        User user1 = stringBean.get("user1");
        Assertions.assertEquals(user1, new User("user1", "name1"));
    }

    @Order(3)
    @Test
    void update() {
        stringBean.update("user1", new User("user1", "name2"));
        User user1 = stringBean.get("user1");
        Assertions.assertEquals(user1, new User("user1", "name2"));
    }

    @Order(4)
    @Test
    void delete() {
        stringBean.delete("user1");
        User user1 = stringBean.get("user1");
        Assertions.assertNull(user1);
    }

}
