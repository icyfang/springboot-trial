package com.example.jetcache.user;

import com.example.basic.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void getUser() {

        User user = userService.getUser(1L);
        System.out.println(user);
    }
}
