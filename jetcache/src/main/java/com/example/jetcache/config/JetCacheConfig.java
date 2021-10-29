package com.example.jetcache.config;

import com.alicp.jetcache.CacheBuilder;
import com.alicp.jetcache.anno.CacheConsts;
import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.support.GlobalCacheConfig;
import com.alicp.jetcache.anno.support.SpringConfigProvider;
import com.alicp.jetcache.embedded.CaffeineCacheBuilder;
import com.alicp.jetcache.embedded.EmbeddedCacheBuilder;
import com.alicp.jetcache.external.ExternalCacheBuilder;
import com.alicp.jetcache.redis.lettuce.RedisLettuceCacheBuilder;
import com.alicp.jetcache.support.FastjsonKeyConvertor;
import com.alicp.jetcache.support.JavaValueDecoder;
import com.alicp.jetcache.support.JavaValueEncoder;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Hodur
 * @date 2021/9/28
 */
@Configuration
@EnableCreateCacheAnnotation
public class JetCacheConfig {

    private final static String REDIS_URI_PREFIX = "redis://";
    private final static String REDIS_SENTINEL_URI_PREFIX = "redis-sentinel://";

    @Value("${spring.redis.password:}")
    String pwd;
    @Value("${spring.redis.sentinel.nodes:}")
    String sentinelNodes;
    @Value("${spring.redis.host}")
    String host;
    @Value("${spring.redis.port}")
    String port;
    @Value("${spring.redis.database}")
    String database;

    @Bean
    public GlobalCacheConfig globalCacheConfig(SpringConfigProvider configProvider, RedisClient redisClient) {
        GlobalCacheConfig globalCacheConfig = new GlobalCacheConfig();
        globalCacheConfig.setConfigProvider(configProvider);
        // statistics interval
        globalCacheConfig.setStatIntervalMinutes(15);
        globalCacheConfig.setAreaInCacheName(false);
        // add local cache builder
        Map<String, CacheBuilder> localBuilders = new HashMap<>(8);
        EmbeddedCacheBuilder<CaffeineCacheBuilder.CaffeineCacheBuilderImpl> localBuilder =
                CaffeineCacheBuilder.createCaffeineCacheBuilder().keyConvertor(FastjsonKeyConvertor.INSTANCE);
        localBuilders.put(CacheConsts.DEFAULT_AREA, localBuilder);
        globalCacheConfig.setLocalCacheBuilders(localBuilders);
        Map<String, CacheBuilder> remoteBuilders = new HashMap<>(8);
        // add remote cache builder
        ExternalCacheBuilder<RedisLettuceCacheBuilder.RedisLettuceCacheBuilderImpl> remoteBuilder =
                RedisLettuceCacheBuilder.createRedisLettuceCacheBuilder()
                        .keyConvertor(FastjsonKeyConvertor.INSTANCE)
                        .valueEncoder(JavaValueEncoder.INSTANCE)
                        .valueDecoder(JavaValueDecoder.INSTANCE)
                        .redisClient(redisClient);
        remoteBuilders.put(CacheConsts.DEFAULT_AREA, remoteBuilder);
        globalCacheConfig.setRemoteCacheBuilders(remoteBuilders);
        return globalCacheConfig;
    }

    @Bean
    public SpringConfigProvider springConfigProvider() {
        return new SpringConfigProvider();
    }

    @Bean
    public RedisClient redisClient() {
        String redisUri = getRedisURL();
        RedisClient client = RedisClient.create(redisUri);
        client.setOptions(ClientOptions.builder()
                .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
                .build());
        return client;
    }

    private String getRedisURL() {
        StringBuilder uri = new StringBuilder();
        String sentinelMaster = null;
        String password = URLEncoder.encode(Optional.ofNullable(pwd).orElse(""), Charset.defaultCharset());
        if (StringUtils.isEmpty(sentinelNodes)) {
            // redis://[password@]host[:port][/databaseNumber][?[timeout=timeout[d|h|m|s|ms|us|ns]]
            uri.append(REDIS_URI_PREFIX);
            if (!StringUtils.isEmpty(password)) {
                uri.append(password).append("@");
            }
            uri.append(host).append(":").append(port);
            if (!StringUtils.isEmpty(database)) {
                uri.append("/").append(database);
            }
        } else {
            // redis-sentinel://[password@]host[:port][,
            // host2[:port2]][/databaseNumber][?[timeout=timeout[d|h|m|s|ms|us|ns]]#sentinelMasterId
            uri.append(REDIS_SENTINEL_URI_PREFIX);
            if (!StringUtils.isEmpty(password)) {
                uri.append(password).append("@");
            }
            uri.append(sentinelNodes);
            if (!StringUtils.isEmpty(database)) {
                uri.append("/").append(database);
            }
            uri.append("#").append(sentinelMaster);
        }
        return uri.toString();
    }

}
