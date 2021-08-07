package com.example.springredis.controller;

import com.example.basic.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Hodur
 * @date 2020-10-22
 */
@Component
public class StringImpl implements CrudInterface {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private ValueOperations<String, Object> valueOperations;

    @PostConstruct
    public void init() {
        valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public User get(String username) {
        Object o = valueOperations.get("string::" + username);
        return (User) o;
    }

    @Override
    public void insert(User user) {
        valueOperations.set("string::" + user.getName(), user);
    }

    @Override
    public void delete(String username) {
        valueOperations.getOperations().delete("string::" + username);
    }

    @Override
    public void update(String username, User user) {
        valueOperations.set("string::" + user.getName(), user);
    }
}
