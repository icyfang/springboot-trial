package com.example.springredis.service;

import com.example.basic.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Hodur
 * @date 2021/10/28
 */
@Component
public class CallbackImpl implements CrudInterface {

    @Autowired
    private StringRedisTemplate stringTemplate;

    @Override
    public User get(String username) {
        return stringTemplate.execute((RedisCallback<User>) connection -> {
            // Can cast to StringRedisConnection if using a StringRedisTemplate
            String s = ((StringRedisConnection) connection).get("callback::" + username);
            try {
                return new ObjectMapper().readValue(s, User.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    @Override
    public void insert(User user) {
        stringTemplate.execute((RedisCallback<User>) connection -> {
            StringRedisConnection stringConnection = (StringRedisConnection) connection;
            try {
                stringConnection.set("callback::" + user.getName(), new JsonMapper().writeValueAsString(user));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    @Override
    public void delete(String username) {

    }

    @Override
    public void update(String username, User user) {

    }
}
