package com.example.swagger.feign.api;

import com.example.swagger.feign.ApiClient;
import com.example.swagger.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * API tests for UserApi
 */
public class UserApiTest {

    private UserApi api;

    @BeforeAll
    public void setup() {
        api = new ApiClient().buildClient(UserApi.class);
    }

    
    /**
     * Create user
     *
     * This can only be done by the logged in user.
     */
    @Test
    public void createUserTest() {
        User body = null;
        // api.createUser(body);

        // TODO: test validations
    }

    
    /**
     * Creates list of users with given input array
     *
     * 
     */
    @Test
    public void createUsersWithArrayInputTest() {
        List<User> body = null;
        // api.createUsersWithArrayInput(body);

        // TODO: test validations
    }

    
    /**
     * Creates list of users with given input array
     *
     * 
     */
    @Test
    public void createUsersWithListInputTest() {
        List<User> body = null;
        // api.createUsersWithListInput(body);

        // TODO: test validations
    }

    
    /**
     * Delete user
     *
     * This can only be done by the logged in user.
     */
    @Test
    public void deleteUserTest() {
        String username = null;
        // api.deleteUser(username);

        // TODO: test validations
    }

    
    /**
     * Get user by user name
     *
     * 
     */
    @Test
    public void getUserByNameTest() {
        String username = null;
        // User response = api.getUserByName(username);

        // TODO: test validations
    }

    
    /**
     * Logs user into the system
     *
     * 
     */
    @Test
    public void loginUserTest() {
        String username = null;
        String password = null;
        // String response = api.loginUser(username, password);

        // TODO: test validations
    }

    /**
     * Logs user into the system
     *
     * 
     *
     * This tests the overload of the method that uses a Map for query parameters instead of
     * listing them out individually.
     */
    @Test
    public void loginUserTestQueryMap() {
        UserApi.LoginUserQueryParams queryParams = new UserApi.LoginUserQueryParams()
            .username(null)
            .password(null);
        // String response = api.loginUser(queryParams);

    // TODO: test validations
    }
    
    /**
     * Logs out current logged in user session
     *
     * 
     */
    @Test
    public void logoutUserTest() {
        // api.logoutUser();

        // TODO: test validations
    }

    
    /**
     * Updated user
     *
     * This can only be done by the logged in user.
     */
    @Test
    public void updateUserTest() {
        String username = null;
        User body = null;
        // api.updateUser(username, body);

        // TODO: test validations
    }

    
}
