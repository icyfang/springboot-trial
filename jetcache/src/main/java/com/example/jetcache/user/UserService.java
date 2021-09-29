package com.example.jetcache.user;

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
}
