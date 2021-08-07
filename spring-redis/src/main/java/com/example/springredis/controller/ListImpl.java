package com.example.springredis.controller;

import com.example.basic.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hodur
 * @date 2020-10-22
 */
@Component
public class ListImpl {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    ListOperations<String, Object> listOperations;

    @PostConstruct
    public void init() {
        listOperations = redisTemplate.opsForList();
    }

    public User pop() {
        Object o = listOperations.leftPop("list::user");
        return ((User) o);
    }

    public void push(User user) {
        listOperations.leftPush("list::user", user);
    }

    public List<User> list() {
        List<Object> range = listOperations.range("list::user", 0, -1);
        return range.stream().map(i -> (User) i).collect(Collectors.toList());
    }
}
