package com.example.jetcache.common;

/**
 * Common cache service
 *
 * @author Hodur
 * @date 2019/9/27
 */
public interface CommonCache {

    <T> T getCache(Class<?> name);

    boolean removeCache(Class<?> name);
}
