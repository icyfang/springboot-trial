package com.example.springredis.controller;

import com.example.basic.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Hodur
 * @date 2020-10-22
 */
@Component
public class StringImpl implements CrudInterface {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public User get(String username) {
        Object o = redisTemplate.opsForValue().get("string::" + username);
        return (User) o;
    }

    @Override
    public void insert(User user) {
        redisTemplate.opsForValue().set("string::" + user.getName(), user);

    }

    @Override
    public void delete(String username) {
        redisTemplate.opsForValue().getOperations().delete("string::" + username);
    }

    @Override
    public void update(String username, User user) {
        redisTemplate.opsForValue().set("string::" + user.getName(), user);
    }
}
