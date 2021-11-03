package com.example.jetcache.holder;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheConfig;
import com.alicp.jetcache.RefreshPolicy;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;

import java.util.concurrent.TimeUnit;

/**
 * @author Hodur
 * @date 2021/9/13
 */
public interface CacheHolder<K, V> extends ApplicationListener<ApplicationPreparedEvent> {

    /**
     * set cache config
     *
     * @return void
     */
    default void setCacheConfig() {

        CacheConfig<K, V> config = getCache().config();
        config.setRefreshPolicy(refreshPolicy());
        //never cache null value, when it is null, cause cache refresh
        config.setCacheNullValue(false);
    }

    /**
     * refresh policy
     *
     * @return com.alicp.jetcache.RefreshPolicy
     */
    default RefreshPolicy refreshPolicy() {
        RefreshPolicy refreshPolicy = RefreshPolicy.newPolicy(30, TimeUnit.MINUTES);
        refreshPolicy.stopRefreshAfterLastAccess(12, TimeUnit.HOURS);
        refreshPolicy.setRefreshLockTimeoutMillis(200);
        return refreshPolicy;
    }

    /**
     * get cache
     *
     * @return com.alicp.jetcache.Cache<K, V>
     */
    Cache<K, V> getCache();

    /**
     * get key
     *
     * @return K
     */
    K getKey();

    @Override
    default void onApplicationEvent(ApplicationPreparedEvent event) {
        getCache().get(getKey());
    }
}
