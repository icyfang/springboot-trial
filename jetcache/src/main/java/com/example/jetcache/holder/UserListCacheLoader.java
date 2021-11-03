package com.example.jetcache.holder;

import com.alicp.jetcache.CacheLoader;
import com.example.basic.model.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author Hodur
 * @date 2021/11/3
 */
@Component
public class UserListCacheLoader implements CacheLoader<String, List<User>> {

    @Override
    public List<User> load(String key) {
        // get all of active component definition
        return Collections.singletonList(new User(1L, "user1", 20));
    }
}
