package com.example.jpa;

import com.example.jpa.async.AsyncService;
import com.example.jpa.base.Forum;
import com.example.jpa.base.ForumRepository;
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
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class AsyncServiceTest {

    @Autowired
    private AsyncService asyncService;

    @Autowired
    private ForumRepository forumRepository;

    @Test
    @Order(1)
    void trans() throws InterruptedException {
        try {
            asyncService.trans(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(1000);
        Assertions.assertEquals(getForum(1), forumRepository.findById((long) 1).orElse(null));
    }

    @Test
    @Order(2)
    void trans2() throws InterruptedException {
        try {
            asyncService.trans2(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(1000);
        Assertions.assertNull(forumRepository.findById((long) 2).orElse(null));
    }

    @Test
    @Order(3)
    void trans3() throws InterruptedException {
        try {
            asyncService.trans3(3, 4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(1000);
        Assertions.assertEquals(getForum(3), forumRepository.findById((long) 3).orElse(null));
        Assertions.assertNull(forumRepository.findById((long) 4).orElse(null));
    }

    private Forum getForum(long i) {
        Forum entity = new Forum();
        entity.setId(i);
        entity.setUserName("username");
        return entity;
    }
}