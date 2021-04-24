package com.example.springcache.controller;

import com.example.springcache.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.BeanUtilsHashMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Hodur
 * @since 2020-09-04
 */
@RestController
@RequestMapping(value = "/object")
public class UserController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserService userService;

    @GetMapping("/user/{userId}")
    public User get(@PathVariable Integer userId) {

        return userService.getUserById(userId);
    }

    @PutMapping("/user")
    public void put(Integer userId, String username, String nickname) {
        User user = new User(userId, username, nickname);
        Map<String, String> map = new BeanUtilsHashMapper<>(User.class).toHash(user);
        redisTemplate.opsForHash().putAll(String.valueOf(userId), map);
    }
}
