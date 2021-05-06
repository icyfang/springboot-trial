package com.example.springredis.controller;

import com.example.springredis.model.User;

/**
 * @author Hodur
 * @date 2020-10-22
 */
public interface CrudInterface {
    User get(String username);

    void insert(User user);

    void delete(String username);

    void update(String username, User user);
}
