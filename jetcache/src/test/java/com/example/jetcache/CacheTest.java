package com.example.jetcache;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheConfig;
import com.example.basic.model.User;
import com.example.jetcache.common.CacheName;
import com.example.jetcache.user.UserCache;
import com.example.jetcache.user.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author Hodur
 * @date 2021/10/11
 */
public class CacheTest {

    @Spy
    UserCache userCache;

    @Mock
    Cache<String, Map<Long, User>> cache;

    @InjectMocks
    UserCache.UserCacheLoader loader = new UserCache.UserCacheLoader();

    @InjectMocks
    UserService userService = new UserService();

    @BeforeAll
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        Field cacheField = this.userCache.getClass().getDeclaredField("tagCodeCache");
        cacheField.setAccessible(true);
        cacheField.set(this.userCache, cache);
        Mockito.when(cache.config()).thenReturn(Mockito.mock(CacheConfig.class));
        userCache.init();

        loader.load(CacheName.C_KEY_USER);
    }

    @Test
    public void getTagCodeByType() {
//        userService.getTagCodeByType("endUser");
    }

}