package com.example.jetcache.user;

import com.alicp.jetcache.Cache;
import com.example.basic.model.User;
import com.example.jetcache.common.CacheName;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author Hodur
 * @date 2021/9/28
 */
@Service
public class UserService {

    @Resource(name = "userCache")
    Cache<String, Map<Long, User>> userCache;

    public User getUser(Long id) {
        return userCache.get(CacheName.C_KEY_USER).get(id);
    }
}
