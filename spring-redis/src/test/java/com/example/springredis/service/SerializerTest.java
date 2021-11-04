package com.example.springredis.service;

import com.example.basic.model.Address;
import com.example.basic.model.User;
import com.example.basic.model.UserVO;
import com.example.springredis.config.RedisConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hodur
 * @date 2021/11/4
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SerializerTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private void printHash(String key) {
        redisTemplate.execute((RedisCallback<User>) connection -> {
            // Can cast to StringRedisConnection if using a StringRedisTemplate
            Map<byte[], byte[]> map = connection.hGetAll(key.getBytes());
            assert map != null;
            map.forEach((k, value) -> System.out.println(new String(k) + " : " + new String(value)));
            return null;
        });
    }

    UserVO userVO = getUserVO();

    private UserVO getUserVO() {
        UserVO userVO = new UserVO(new Address("Amaurote", "Utopia"));
        userVO.setId(1L);
        userVO.setAge(20);
        userVO.setName("user1");
        return userVO;
    }

    @Test
    @Order(1)
    public void testGenericToStringSerializer() throws JsonProcessingException {
        redisTemplate.setValueSerializer(RedisConfig.getGenericToStringSerializer());
        redisTemplate.opsForList().leftPushAll("GenericToStringSerializer", userVO);
        List<Object> userList = redisTemplate.opsForList().range("GenericToStringSerializer", 0, -1);
        assert userList != null;
        Assertions.assertEquals(userVO, new ObjectMapper().readValue((String) userList.get(0), UserVO.class));
    }

    @Test
    @Order(2)
    public void testStringRedisSerializer() {

        redisTemplate.setValueSerializer(new StringRedisSerializer());
        Assertions.assertThrows(ClassCastException.class, () -> {
            pushAndRange("StringRedisSerializer");
        }, "class com.example.basic.model.UserVO cannot be cast to class java.lang.String (com.example.basic.model" +
                ".UserVO is in unnamed module of loader 'app'; java.lang.String is in module java.base of loader " +
                "'bootstrap')");
    }

    @Test
    @Order(3)
    public void testJackson2JsonRedisSerializer() {
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        redisTemplate.opsForList().leftPushAll("Jackson2JsonRedisSerializer", userVO);
        List<Object> userList = redisTemplate.opsForList().range("Jackson2JsonRedisSerializer", 0, -1);
        assert userList != null;
        Map<String, Object> expected = getExpectedMap();
        Assertions.assertEquals(expected, userList.get(0));
    }

    @Test
    @Order(4)
    public void testGenericJackson2JsonRedisSerializer() {
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        pushAndRange("GenericJackson2JsonRedisSerializer");
    }

    private void pushAndRange(String stringRedisSerializer) {
        redisTemplate.opsForList().leftPushAll(stringRedisSerializer, userVO);
        List<Object> userList = redisTemplate.opsForList().range(stringRedisSerializer, 0, -1);
        assert userList != null;
        Assertions.assertEquals(userVO, userList.get(0));
    }

    private Map<String, Object> getExpectedMap() {
        Map<String, Object> expected = new LinkedHashMap<>();
        expected.put("id", 1);
        expected.put("name", "user1");
        expected.put("age", 20);
        Map<String, Object> expectedAddress = new LinkedHashMap<>();
        expectedAddress.put("city", "Amaurote");
        expectedAddress.put("country", "Utopia");
        expected.put("address", expectedAddress);
        return expected;
    }
}
