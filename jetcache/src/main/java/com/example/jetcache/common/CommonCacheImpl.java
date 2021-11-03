package com.example.jetcache.common;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheConfig;
import com.alicp.jetcache.RefreshPolicy;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * Common cache service implementation
 *
 * @author Hodur
 * @date 2019/9/27
 */
@Component
@Slf4j
public class CommonCacheImpl<T> implements CommonCache, ApplicationContextAware {

    @CreateCache(name = "commonCache:", localLimit = 100, cacheType = CacheType.REMOTE)
    private Cache<Class<?>, T> allCache;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private CommonCacheLoader<T> commonCacheLoader;

    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        RefreshPolicy refreshPolicy = RefreshPolicy.newPolicy(
                getIntProperties("gnr.refresh.interval", 10), TimeUnit.MINUTES);
        refreshPolicy.stopRefreshAfterLastAccess(
                getIntProperties("gnr.refresh.stop.limit", 30), TimeUnit.MINUTES);
        refreshPolicy.setRefreshLockTimeoutMillis(
                getIntProperties("gnr.refresh.lock.timeout", 2000));
        CacheConfig<Class<?>, T> config = allCache.config();
        config.setLoader(commonCacheLoader);
        config.setRefreshPolicy(refreshPolicy);
    }

    @Override
    public <T> T getCache(Class<?> name) {
        return (T) allCache.get(name);
    }

    @Override
    public boolean removeCache(Class<?> name) {
        return allCache.remove(name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private int getIntProperties(String key, int defaultValue) {
        if (applicationContext == null) {
            log.warn("The application context haven't be initialized, just use default property value");
            return defaultValue;
        }
        Environment env = applicationContext.getEnvironment();
        String value = env.getProperty(key);
        if (!StringUtils.isEmpty(value)) {
            return Integer.parseInt(value);
        }
        return defaultValue;
    }
}
