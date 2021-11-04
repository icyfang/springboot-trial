package com.example.springredis.service;

import com.example.basic.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Hodur
 * @date 2020-10-22
 */
@Component
public class StringImpl implements CrudInterface {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Resource(name = "redisTemplate")
    ValueOperations<String, Object> valueOperations;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

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

    public User getByBound(String username) {
        BoundValueOperations<String, Object> boundValueOperations = redisTemplate.boundValueOps(username);
        Object o = boundValueOperations.get();
        return (User) o;
    }

    public void insertByBound(User user) {
        BoundValueOperations<String, Object> boundValueOperations = redisTemplate.boundValueOps(user.getName());
        boundValueOperations.set(user);
    }

    public void deleteByBound(String username) {
        BoundValueOperations<String, Object> boundValueOperations = redisTemplate.boundValueOps(username);
        // BoundValueOperations can not delete the key, that does make sense
        boundValueOperations.getOperations().delete(username);
    }

    public void updateByBound(String username, User user) {
        BoundValueOperations<String, Object> boundValueOperations = redisTemplate.boundValueOps(username);
        boundValueOperations.set(user);
    }

}
