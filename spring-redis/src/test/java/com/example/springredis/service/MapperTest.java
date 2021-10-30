package com.example.springredis.service;

import com.example.basic.model.Address;
import com.example.basic.model.User;
import com.example.basic.model.UserVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.BeanUtilsHashMapper;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.util.Base64;
import java.util.Map;

/**
 * @author Hodur
 * @date 2021/10/29
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MapperTest {

    @Resource(name = "redisTemplate")
    HashOperations<String, Object, Object> hashOperations;

    @Resource(name = "redisTemplate")
    HashOperations<String, byte[], byte[]> byteHashOperations;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    BeanUtilsHashMapper<User> beanUtilsHashMapper = new BeanUtilsHashMapper<>(User.class);

    HashMapper<Object, byte[], byte[]> hashMapper = new ObjectHashMapper();

    Jackson2HashMapper flattenJackson2HashMapper = new Jackson2HashMapper(true);

    Jackson2HashMapper nonFlattenJackson2HashMapper = new Jackson2HashMapper(false);

    UserVO user = getUserVO();

    private UserVO getUserVO() {
        UserVO userVO = new UserVO(new Address("Amaurote", "Utopia"));
        userVO.setId(1L);
        userVO.setAge(20);
        userVO.setName("user1");
        return userVO;
    }

    @Test
    @Order(1)
    public void insertByBeanUtilMapper() {
        Map<String, String> map = beanUtilsHashMapper.toHash(user);
        String key = "beanUtilMapper::" + user.getName();
        hashOperations.putAll(key, map);
        printHash(key);
    }

    @Test
    @Order(3)
    public void insertByFlattenJackson2HashMapper() {
        Map<String, Object> map = flattenJackson2HashMapper.toHash(user);
        String key = "flattenJackson2HashMapper::" + user.getName();
        hashOperations.putAll(key, map);
        printHash(key);
    }

    @Test
    @Order(4)
    public void insertByNonFlattenJackson2HashMapper() {
        Map<String, Object> map = nonFlattenJackson2HashMapper.toHash(user);
        String key = "nonFlattenJackson2HashMapper::" + user.getName();
        hashOperations.putAll(key, map);
        printHash(key);
    }

    @Test
    @Order(5)
    public void insertByHashMapper() throws JsonProcessingException {
        JdkSerializationRedisSerializer hashKeySerializer = new JdkSerializationRedisSerializer();
        redisTemplate.setHashKeySerializer(hashKeySerializer);
        Map<byte[], byte[]> map = hashMapper.toHash(user);
        String key = "hashMapper::" + user.getName();
        hashOperations.putAll(key, map);

        Map<Object, Object> entries = hashOperations.entries(key);
        System.out.println(new ObjectMapper().writeValueAsString(entries));
        Base64.Decoder decoder = Base64.getDecoder();
        entries.forEach((k, v) -> System.out
                .println(new String((byte[]) k) + ":" + new String(decoder.decode((String) v))));

        redisTemplate.execute((RedisCallback<User>) connection -> {
            // Can cast to StringRedisConnection if using a StringRedisTemplate
            Map<byte[], byte[]> returnMap = connection.hGetAll(key.getBytes());
            assert returnMap != null;
            returnMap.forEach((k, value) -> {
                byte[] deserialize = (byte[]) hashKeySerializer.deserialize(k);
                // remove comma at the start and end
                byte[] value1 = new byte[value.length - 2];
                System.arraycopy(value, 1, value1, 0, value.length - 2);
                System.out.println(new String(deserialize) + " : " + new String(decoder.decode(value1)));
            });
            return null;
        });
    }

    private void printHash(String key) {
        redisTemplate.execute((RedisCallback<User>) connection -> {
            // Can cast to StringRedisConnection if using a StringRedisTemplate
            Map<byte[], byte[]> map = connection.hGetAll(key.getBytes());
            assert map != null;
            map.forEach((k, value) -> System.out.println(new String(k) + " : " + new String(value)));
            return null;
        });
    }
}
