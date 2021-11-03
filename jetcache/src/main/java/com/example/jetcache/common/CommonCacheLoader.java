package com.example.jetcache.common;

import com.alicp.jetcache.CacheLoader;
import com.example.jetcache.ApplicationContextHolder;
import org.springframework.stereotype.Component;

/**
 * description
 *
 * @author Hodur
 * @date 2019/10/3
 */
@Component
public class CommonCacheLoader<T> implements CacheLoader<Class<?>, T> {

    @Override
    public T load(Class<?> key) throws Throwable {
        CacheLoader<String, T> loader =
                (CacheLoader<String, T>) ApplicationContextHolder.getApplicationContext().getBean(key);
        return loader.load(key.getSimpleName());
    }

}
