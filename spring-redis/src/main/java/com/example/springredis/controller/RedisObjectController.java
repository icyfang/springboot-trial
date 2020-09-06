package com.example.springredis.controller;

import com.example.springredis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.BeanUtilsHashMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Shanghong Cai
 * @since 2020-09-04
 */
@RestController
@RequestMapping(value = "/object")
public class RedisObjectController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/user/{username}")
    public User get(@PathVariable String username) {
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(username);
        Map<String, String> collect = entries.entrySet().stream()
                                             .collect(Collectors
                                                     .toMap(i -> (String) i.getKey(), i -> (String) i.getValue()));
        return new BeanUtilsHashMapper<>(User.class).fromHash(collect);
    }

    @PutMapping("/user")
    public void put(String username, String nickname) {
        User user = new User(username, nickname);
        Map<String, String> map = new BeanUtilsHashMapper<>(User.class).toHash(user);
        redisTemplate.opsForHash().putAll(username, map);
    }
}
