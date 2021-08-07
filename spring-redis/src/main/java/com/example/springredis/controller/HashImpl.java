package com.example.springredis.controller;

import com.example.basic.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.BeanUtilsHashMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Hodur
 * @date 2020-09-04
 */
@Component
public class HashImpl implements CrudInterface {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private HashOperations<String, Object, Object> hashOperations;

    @PostConstruct
    public void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public User get(String username) {
        Map<Object, Object> entries = hashOperations.entries("hash::" + username);
        Map<String, String> collect = entries.entrySet().stream()
                                             .collect(Collectors
                                                     .toMap(i -> (String) i.getKey(), i -> (String) i.getValue()));
        return new BeanUtilsHashMapper<>(User.class).fromHash(collect);
    }

    @Override
    public void insert(User user) {
        Map<String, String> map = new BeanUtilsHashMapper<>(User.class).toHash(user);
        hashOperations.putAll("hash::" + user.getName(), map);
    }

    @Override
    public void delete(String username) {
        hashOperations.getOperations().delete("hash::" + username);
    }

    @Override
    public void update(String username, User user) {
        Map<String, String> map = new BeanUtilsHashMapper<>(User.class).toHash(user);
        hashOperations.putAll("hash::" + username, map);
    }
}
