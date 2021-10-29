package com.example.springredis.service;

import com.example.basic.model.User;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hodur
 * @date 2020-10-22
 */
@Component
public class ListImpl {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Resource(name = "redisTemplate")
    ListOperations<String, Object> listOperations;

    public User pop() {

        Object o = listOperations.leftPop("list::user");
        return ((User) o);
    }

    public void push(User user) {
        listOperations.leftPush("list::user", user);
    }

    public List<User> list() {
        List<Object> range = listOperations.range("list::user", 0, -1);
        return range.stream().map(i -> (User) i).collect(Collectors.toList());
    }
}
