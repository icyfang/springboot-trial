package com.example.springredis.service;

import com.example.basic.model.User;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Hodur
 * @date 2020-10-22
 */
@Component
public class SetImpl {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Resource(name = "redisTemplate")
    SetOperations<String, Object> setOperations;

    public User pop() {
        Object pop = setOperations.pop("set::user");
        return ((User) pop);
    }

    public void push(User user) {
        setOperations.add("set::user", user);
    }

    public List<User> list() {
        Set<Object> members = setOperations.members("set::user");
        return members.stream().map(i -> (User) i).collect(Collectors.toList());
    }
}
