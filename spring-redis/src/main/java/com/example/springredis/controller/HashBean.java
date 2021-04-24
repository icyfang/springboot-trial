package com.example.springredis.controller;

import com.example.springredis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.BeanUtilsHashMapper;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Hodur
 * @since 2020-09-04
 */
@Component
public class HashBean implements CrudInterface {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public User get(String username) {
        Map<Object, Object> entries = redisTemplate.opsForHash().entries("hash::" + username);
        Map<String, String> collect = entries.entrySet().stream()
                                             .collect(Collectors
                                                     .toMap(i -> (String) i.getKey(), i -> (String) i.getValue()));
        return new BeanUtilsHashMapper<>(User.class).fromHash(collect);
    }

    @Override
    public void insert(User user) {
        Map<String, String> map = new BeanUtilsHashMapper<>(User.class).toHash(user);
        redisTemplate.opsForHash().putAll("hash::" + user.getName(), map);
    }

    @Override
    public void delete(String username) {
        redisTemplate.opsForHash().getOperations().delete("hash::" + username);
    }

    @Override
    public void update(String username, User user) {
        Map<String, String> map = new BeanUtilsHashMapper<>(User.class).toHash(user);
        redisTemplate.opsForHash().putAll("hash::" + username, map);
    }
}
