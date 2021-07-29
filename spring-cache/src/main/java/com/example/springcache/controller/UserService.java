package com.example.springcache.controller;

import com.example.basic.model.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hodur
 * @date 2020-09-04
 */
@Service
@CacheConfig(cacheNames = "user")
public class UserService {

    Map<Long, User> users = new HashMap<>();

    @Cacheable(key = "#id")
    public User getUser(Long id) {
        return users.get(id);
    }

    @CachePut(key = "#user.id")
    public User createUser(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @CacheEvict(key = "#id")
    public Boolean updateUser(Long id, User user) {
        users.put(id, user);
        return true;
    }

    @CacheEvict(key = "#id")
    public Boolean deleteUser(Long id) {
        users.remove(id);
        return true;
    }

}



