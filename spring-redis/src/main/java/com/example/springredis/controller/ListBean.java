package com.example.springredis.controller;

import com.example.springredis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hodur
 * @date 2020-10-22
 */
@Component
public class ListBean {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public User pop() {
        Object o = redisTemplate.opsForList().leftPop("list::user");
        return ((User) o);
    }

    public void push(User user) {
        redisTemplate.opsForList().leftPush("list::user", user);
    }

    public List<User> list() {
        List<Object> range = redisTemplate.opsForList().range("list::user", 0, -1);
        return range.stream().map(i -> (User) i).collect(Collectors.toList());
    }
}
