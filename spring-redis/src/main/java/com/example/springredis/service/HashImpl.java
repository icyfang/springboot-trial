package com.example.springredis.service;

import com.example.basic.model.User;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.hash.BeanUtilsHashMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Hodur
 * @date 2020-09-04
 */
@Component
public class HashImpl implements CrudInterface {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Resource(name = "redisTemplate")
    HashOperations<String, Object, Object> hashOperations;

    BeanUtilsHashMapper<User> beanUtilsHashMapper = new BeanUtilsHashMapper<>(User.class);

    @Override
    public User get(String username) {
        Map<Object, Object> entries = hashOperations.entries("hash::" + username);
        Map<String, String> collect = entries.entrySet().stream()
                .collect(Collectors.toMap(i -> (String) i.getKey(), i -> (String) i.getValue()));
        return beanUtilsHashMapper.fromHash(collect);
    }

    @Override
    public void insert(User user) {
        Map<String, String> map = beanUtilsHashMapper.toHash(user);
        hashOperations.putAll("hash::" + user.getName(), map);
    }

    @Override
    public void delete(String username) {
        hashOperations.getOperations().delete("hash::" + username);
    }

    @Override
    public void update(String username, User user) {
        Map<String, String> map = beanUtilsHashMapper.toHash(user);
        hashOperations.putAll("hash::" + username, map);
    }
}
