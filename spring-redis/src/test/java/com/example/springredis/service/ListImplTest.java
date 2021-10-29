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

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ListImplTest {

    @Autowired
    private ListImpl listImpl;

    @Test
    @Order(1)
    void push() {
        User user = new User((long) 1, "user1", 20);
        listImpl.push(user);
        List<User> list = listImpl.list();
        Assertions.assertEquals(list, Collections.singletonList(new User((long) 1, "user1", 20)));
    }

    @Test
    @Order(2)
    void pop() {
        User pop = listImpl.pop();
        Assertions.assertEquals(pop, new User((long) 1, "user1", 20));
        List<User> list = listImpl.list();
        Assertions.assertEquals(list, Collections.emptyList());
    }


}
