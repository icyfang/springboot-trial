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

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ListBeanTest {

    @Autowired
    private ListBean listBean;

    @Test
    @Order(2)
    void pop() {
        User pop = listBean.pop();
        Assertions.assertEquals(pop, new User("user1", "name1"));
        List<User> list = listBean.list();
        Assertions.assertEquals(list, Collections.emptyList());
    }

    @Test
    @Order(1)
    void push() {
        User user = new User("user1", "name1");
        listBean.push(user);
        List<User> list = listBean.list();
        Assertions.assertEquals(list, Collections.singletonList(new User("user1", "name1")));
    }

}
