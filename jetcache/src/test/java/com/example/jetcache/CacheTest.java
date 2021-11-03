package com.example.jetcache;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheConfig;
import com.example.basic.model.User;
import com.example.jetcache.common.CacheName;
import com.example.jetcache.holder.UserCacheHolder;
import com.example.jetcache.holder.UserCacheLoader;
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

import static org.mockito.Mockito.when;

/**
 * @author Hodur
 * @date 2021/10/11
 */
public class CacheTest {

    @Spy
    UserCacheHolder userCacheHolder;

    @Mock
    Cache<String, Map<Long, User>> cache;

    @InjectMocks
    UserCacheLoader loader = new UserCacheLoader();

    @InjectMocks
    UserService userService = new UserService();

    @BeforeAll
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        Field cacheField = this.userCacheHolder.getClass().getDeclaredField("tagCodeCache");
        cacheField.setAccessible(true);
        cacheField.set(this.userCacheHolder, cache);
        when(cache.config()).thenReturn(Mockito.mock(CacheConfig.class));
        userCacheHolder.init();

        loader.load(CacheName.C_KEY_USER);
    }

    @Test
    public void getTagCodeByType() {
//        userService.getTagCodeByType("endUser");
    }

}
