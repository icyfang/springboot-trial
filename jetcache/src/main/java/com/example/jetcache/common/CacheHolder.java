package com.example.jetcache.common;

import com.alicp.jetcache.Cache;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author Hodur
 * @date 2021/9/28
 */
public interface CacheHolder<K, V> extends ApplicationListener<ApplicationPreparedEvent> {

    default V get(K k) {
        return getCache().get(k);
    }

    Cache<K, V> getCache();

    K getKey();

    @Override
    default void onApplicationEvent(ApplicationPreparedEvent event) {
        getCache().get(getKey());
    }
}
