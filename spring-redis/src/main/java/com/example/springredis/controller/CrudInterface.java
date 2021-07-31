package com.example.springredis.controller;

import com.example.basic.model.User;

/**
 * @author Hodur
 * @date 2020-10-22
 */
public interface CrudInterface {

    /**
     * get
     *
     * @param username username
     * @return com.example.basic.model.User
     */
    User get(String username);

    /**
     * insert
     *
     * @param user user
     */
    void insert(User user);

    /**
     * delete
     *
     * @param username username
     */
    void delete(String username);

    /**
     * update
     *
     * @param username username
     * @param user     user
     */
    void update(String username, User user);
}
