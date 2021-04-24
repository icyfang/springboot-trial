package com.example.springcache.controller;

import com.example.springcache.model.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author Hodur
 * @since 2020-09-04
 */
@Service
@CacheConfig(cacheNames = "cache-1")
public class UserService {

    @Cacheable(key = "#id")
    public User getUserById(Integer id) {
        return new User();
    }
}



