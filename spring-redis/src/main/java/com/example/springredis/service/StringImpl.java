package com.example.springredis.service;

import com.example.basic.model.User;
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
