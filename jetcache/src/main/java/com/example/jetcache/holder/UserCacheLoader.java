package com.example.jetcache.holder;

import com.alicp.jetcache.CacheLoader;
import com.example.basic.model.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hodur
 * @date 2021/11/3
 */
@Component
public class UserCacheLoader implements CacheLoader<String, Map<Long, User>> {

    @Override
    public Map<Long, User> load(String key) {
        // get all of active component definition
        Map<Long, User> collect = new HashMap<>();
        collect.put(1L, new User(1L, "user1", 20));
        return collect;
    }
}
