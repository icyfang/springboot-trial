package com.example.springredis.controller;

import com.example.springredis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Hodur
 * @since 2020-10-22
 */
@Component
public class SetBean {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public User pop() {
        Object pop = redisTemplate.opsForSet().pop("set::user");
        return ((User) pop);
    }

    public void push(User user) {
        redisTemplate.opsForSet().add("set::user", user);
    }

    public List<User> list() {
        Set<Object> members = redisTemplate.opsForSet().members("set::user");
        return members.stream().map(i -> (User) i).collect(Collectors.toList());
    }
}
