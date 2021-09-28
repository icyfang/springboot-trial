package com.example.jetcache.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author Hodur
 * @date 9/28/2021 10:35 AM
 */
@Service
public class UserService {

    @Autowired
    private UserCache userCache;
}
