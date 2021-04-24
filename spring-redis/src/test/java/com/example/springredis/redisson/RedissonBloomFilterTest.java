package com.example.springredis.redisson;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Hodur
 * @date 2021/4/07
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RedissonBloomFilterTest {

    @Autowired
    private RedissonBloomFilter filter;

    @Test
    public void testDoFilter() {
        boolean foo = filter.doFilter("foo");
        Assertions.assertFalse(foo);

        filter.addEle("foo");
        foo = filter.doFilter("foo");
        Assertions.assertTrue(foo);
    }
}