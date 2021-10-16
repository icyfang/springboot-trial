package com.example.jetcache.user;

import com.example.basic.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Hodur
 * @date 2021/9/28
 */
@Service
public class UserService {

    @Autowired
    private UserCache userCache;

    public User getUser(Long id) {
        return userCache.get().get(id);
    }
}
