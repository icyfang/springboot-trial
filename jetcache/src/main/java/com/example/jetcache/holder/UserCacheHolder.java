package com.example.jetcache.holder;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.example.basic.model.User;
import com.example.jetcache.common.CacheName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * description
 *
 * @author candicey
 * @date 0020/12/20
 */
@Component
public class UserCacheHolder implements CacheHolder<String, Map<Long, User>> {

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

}
