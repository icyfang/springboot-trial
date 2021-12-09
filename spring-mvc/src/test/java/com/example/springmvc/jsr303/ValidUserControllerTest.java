package com.example.springmvc.jsr303;

import com.example.springmvc.MvcTestHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.util.NestedServletException;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ValidUserControllerTest extends MvcTestHelper {

    @Autowired
    private ValidUserController validUserController;

    MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(validUserController).build();
    }

    @Override
    protected MockMvc getMockMvc() {
        return mockMvc;
    }

    @Test
    @Order(1)
    void postUser() throws Exception {

        ValidUser user = new ValidUser(1L, "name", 18);
        String contentAsString = getResponseContent("/validation/group/single", user);
        String responseEntity = new ObjectMapper().convertValue(contentAsString, String.class);
        assertEquals("success", responseEntity);
    }

    /**
     * group validation, using group single, expecting exception.
     */
    @Test
    @Order(2)
    void postUserWithInvalidAge() {
        ValidUser user = new ValidUser(1L, "name", 10);

        NestedServletException exception =
                responseException("/validation/group/single", user, NestedServletException.class);
        ConstraintViolationException cause = ((ConstraintViolationException) exception.getCause());
        assertEquals("postUser.user.age: age 应当大于 18", cause.getMessage());
    }

    @Test
    @Order(3)
    void PostBatchUser() throws Exception {

        ValidUser user = new ValidUser(2L, "name1", 10);
        String contentAsString = getResponseContent("/validation/group/batch", Collections.singleton(user));
        String responseEntity = new ObjectMapper().convertValue(contentAsString, String.class);
        assertEquals("success", responseEntity);
    }

    @Test
    @Order(4)
    void PostBatchUserWithInvalidName() {
        ValidUser user = new ValidUser(2L, "name", 10);

        NestedServletException exception = responseException("/validation/group/batch", Collections.singletonList(user),
                NestedServletException.class);
        ConstraintViolationException cause = ((ConstraintViolationException) exception.getCause());
        assertEquals("postUserInBatch.user[0].name: name 长度应该在 5~10 之间", cause.getMessage());
    }

    @Test
    @Order(5)
    void postCustomUserWithInvalidAge() throws Exception {

        ValidUser user = new ValidUser(3L, "name", 10);
        MethodArgumentNotValidException resolvedException = ((MethodArgumentNotValidException)
                performPostRequest("/validation/custom", user).andReturn().getResolvedException());
        List<String> collect = resolvedException.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        assertTrue(collect.contains("age 应当大于 18"));
    }

    @Test
    @Order(6)
    void postCustomUserWithInvalidName() {
        ValidUser user = new ValidUser(3L, "name", 18);

        NestedServletException exception = responseException("/validation/custom", user, NestedServletException.class);
        ConstraintViolationException cause = ((ConstraintViolationException) exception.getCause());
        assertEquals("postCustomUser.user: 错误 username", cause.getMessage());
    }

    @Test
    @Order(7)
    void postCustomUser() throws Exception {

        ValidUser user = new ValidUser(3L, "user", 18);
        String contentAsString = getResponseContent("/validation/custom", user);
        String responseEntity = new ObjectMapper().convertValue(contentAsString, String.class);
        assertEquals("success", responseEntity);
    }

    @Test
    @Order(8)
    void postNestedUserWithInvalidAge() {
        NestedUser user = new NestedUser(4, "name", 10, new NestedUser.Address(1L, "abcde"));

        NestedServletException exception = responseException("/validation/nest", user, NestedServletException.class);
        ConstraintViolationException cause = ((ConstraintViolationException) exception.getCause());
        assertEquals("postNestedUser.user.age: age 应当大于 18", cause.getMessage());
    }

    @Test
    @Order(9)
    void postNestedUserWithInvalidAddress() throws Exception {

        NestedUser user = new NestedUser(4, "name", 18, new NestedUser.Address(1L, "abcd"));

        MethodArgumentNotValidException resolvedException = ((MethodArgumentNotValidException)
                performPostRequest("/validation/nest", user).andReturn().getResolvedException());
        List<String> collect = resolvedException.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        assertTrue(collect.contains("国家名长度应该在 5~10 之间"));
    }

    @Test
    @Order(10)
    void postNestedUser() throws Exception {

        NestedUser user = new NestedUser(4, "user", 18, new NestedUser.Address(1L, "abcde"));
        String contentAsString = getResponseContent("/validation/nest", user);
        String responseEntity = new ObjectMapper().convertValue(contentAsString, String.class);
        assertEquals("success", responseEntity);
    }

    @Test
    @Order(11)
    void getUserWithInvalidId() {

        NestedServletException exception =
                assertThrows(NestedServletException.class, () -> performGetRequest("/validation/path/1"));
        ConstraintViolationException cause = ((ConstraintViolationException) exception.getCause());
        assertEquals("getUser.id: 最小不能小于3", cause.getMessage());
    }

    @Test
    @Order(12)
    void getUser() throws Exception {
        String contentAsString = getResponseContent("/validation/path/4");
        assertEquals("{\"id\":4,\"name\":\"user\",\"age\":18,\"address\":{\"id\":1,\"country\":\"abcde\"}}",
                contentAsString);
    }

    @Test
    @Order(13)
    void getUserByQueryWithInvalidId() {

        NestedServletException exception =
                assertThrows(NestedServletException.class, () -> performGetRequest("/validation/query?id=1"));
        ConstraintViolationException cause = ((ConstraintViolationException) exception.getCause());
        assertEquals("getUserByQuery.id: 最小不能小于3", cause.getMessage());
    }

    @Test
    @Order(14)
    void testGetUserByQuery() throws Exception {
        String contentAsString = getResponseContent("/validation/query?id=4");
        assertEquals("{\"id\":4,\"name\":\"user\",\"age\":18,\"address\":{\"id\":1,\"country\":\"abcde\"}}",
                contentAsString);
    }
}
