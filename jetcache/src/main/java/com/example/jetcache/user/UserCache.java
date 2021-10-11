package com.example.jetcache.user;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheLoader;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.example.basic.model.User;
import com.example.jetcache.common.CacheHolder;
import com.example.jetcache.common.CacheName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Map;

/**
 * @author Hodur
 * @date 2021/9/28
 */
@Component
public class UserCache implements CacheHolder<String, Map<Long, User>> {

    @CreateCache(name = "user:", cacheType = CacheType.REMOTE)
    private Cache<String, Map<Long, User>> userCache;

    @Autowired
    private UserCacheLoader cacheLoader;

    @PostConstruct
    public void init() {
        setCacheConfig();
        userCache.config().setLoader(cacheLoader);
    }

    @Override
    public Cache<String, Map<Long, User>> getCache() {
        return userCache;
    }

    @Override
    public String getKey() {
        return CacheName.C_KEY_USER;
    }

    @Component
    public static class UserCacheLoader implements CacheLoader<String, Map<Long, User>> {

        @Override
        public Map<Long, User> load(String key) {
            // get all of active component definition
            Map<Long, User> collect = Collections.emptyMap();
            return collect;
        }

    }
}
