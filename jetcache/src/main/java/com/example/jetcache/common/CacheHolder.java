package com.example.jetcache.common;

import com.alicp.jetcache.Cache;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;

/**
 * <p>
 *
 * </p>
 *
 * @author Hodur
 * @date 9/13/2021 2:14 PM
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
