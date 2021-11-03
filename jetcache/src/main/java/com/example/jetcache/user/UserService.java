package com.example.jetcache.user;

import com.alicp.jetcache.Cache;
import com.example.basic.model.User;
import com.example.jetcache.common.CacheName;
import com.example.jetcache.common.CommonCache;
import com.example.jetcache.holder.UserCacheLoader;
import com.example.jetcache.holder.UserListCacheLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Hodur
 * @date 2021/9/28
 */
@Service
public class UserService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Resource(name = "userCacheHolder")
    Cache<String, Map<Long, User>> userCache;

    @Autowired
    private CommonCache commonCache;

    public User getUser(Long id) {
        return userCache.get(CacheName.C_KEY_USER).get(id);
    }

    public User fetchUser(Long id) {
        Map<Long, User> cache = commonCache.getCache(UserCacheLoader.class);
        return cache.get(id);
    }

    public User retrieveUser(Long id) {
        List<User> cache = commonCache.getCache(UserListCacheLoader.class);
        return cache.get(0);
    }
}
