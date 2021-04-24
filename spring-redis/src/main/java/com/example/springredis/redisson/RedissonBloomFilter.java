package com.example.springredis.redisson;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Hodur
 * @date 2021/4/07
 */
@Component
public class RedissonBloomFilter {

    @Autowired
    RedissonClient redisson;

    @PostConstruct
    void initBloomFilter() {
        RBloomFilter<String> bloomFilter = redisson.getBloomFilter("phoneList");
        //初始化布隆过滤器：预计元素为100000000L,误差率为3%
        bloomFilter.tryInit(100000000L, 0.03);
    }

    public boolean doFilter(String s) {

        return redisson.getBloomFilter("phoneList").contains(s);
    }

    public void addEle(String s) {
        redisson.getBloomFilter("phoneList").add(s);
    }
}
