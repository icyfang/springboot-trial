package com.example.springmvc.jsr303;

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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.util.NestedServletException;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ValidUserControllerTest {

    @Autowired
    private ValidUserController validUserController;

    MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(validUserController).build();
    }

    @Test
    @Order(1)
    void postUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ValidUser user = new ValidUser((long) 1, "name", 18);
        String contentAsString = mockMvc.perform(post("/validation/single").content(mapper.writeValueAsString(user))
                                                                           .accept(MediaType.APPLICATION_JSON)
                                                                           .contentType(MediaType.APPLICATION_JSON))
                                        .andReturn()
                                        .getResponse()
                                        .getContentAsString();
        String responseEntity = mapper.convertValue(contentAsString, String.class);
        assertEquals("success", responseEntity);

    }

    @Test
    @Order(2)
    void postUserWithInvalidAge() {
        ObjectMapper mapper = new ObjectMapper();
        ValidUser user = new ValidUser((long) 1, "name", 10);

        NestedServletException exception = assertThrows(NestedServletException.class, () -> mockMvc
                .perform(post("/validation/single").content(mapper.writeValueAsString(user))
                                                   .accept(MediaType.APPLICATION_JSON)
                                                   .locale(Locale.CHINA)
                                                   .contentType(MediaType.APPLICATION_JSON)));
        ConstraintViolationException cause = ((ConstraintViolationException) exception.getCause());
        assertEquals("postUser.user.age: age 应当大于 18", cause.getMessage());
    }

    @Test
    @Order(3)
    void postCustomUserWithInvalidAge() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        ValidUser user = new ValidUser((long) 2, "name", 10);
        MethodArgumentNotValidException resolvedException = ((MethodArgumentNotValidException) mockMvc
                .perform(post("/validation/custom").content(mapper.writeValueAsString(user))
                                                   .accept(MediaType.APPLICATION_JSON)
                                                   .locale(Locale.CHINA)
                                                   .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResolvedException());
        List<String> collect = resolvedException.getBindingResult().getAllErrors().stream()
                                                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                                .collect(Collectors.toList());
        assertTrue(collect.contains("age 应当大于 18"));
    }

    @Test
    @Order(4)
    void postCustomUserWithInvalidName() {
        ObjectMapper mapper = new ObjectMapper();
        ValidUser user = new ValidUser((long) 2, "name", 18);

        NestedServletException exception = assertThrows(NestedServletException.class, () -> mockMvc
                .perform(post("/validation/custom").content(mapper.writeValueAsString(user))
                                                   .accept(MediaType.APPLICATION_JSON)
                                                   .contentType(MediaType.APPLICATION_JSON)));
        ConstraintViolationException cause = ((ConstraintViolationException) exception.getCause());
        assertEquals("postCustomUser.user: 错误 username", cause.getMessage());
    }

    @Test
    @Order(5)
    void postCustomUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ValidUser user = new ValidUser((long) 2, "user", 18);
        String contentAsString = mockMvc.perform(post("/validation/custom").content(mapper.writeValueAsString(user))
                                                                           .accept(MediaType.APPLICATION_JSON)
                                                                           .contentType(MediaType.APPLICATION_JSON))
                                        .andReturn()
                                        .getResponse()
                                        .getContentAsString();
        String responseEntity = mapper.convertValue(contentAsString, String.class);
        assertEquals("success", responseEntity);
    }

    @Test
    @Order(6)
    void postNestedUserWithInvalidAge() {
        ObjectMapper mapper = new ObjectMapper();
        NestedUser user = new NestedUser(3, "name", 10, new NestedUser.Address((long) 1, "abcde"));

        NestedServletException exception = assertThrows(NestedServletException.class, () -> mockMvc
                .perform(post("/validation/nest").content(mapper.writeValueAsString(user))
                                                 .accept(MediaType.APPLICATION_JSON)
                                                 .locale(Locale.CHINA)
                                                 .contentType(MediaType.APPLICATION_JSON)));
        ConstraintViolationException cause = ((ConstraintViolationException) exception.getCause());
        assertEquals("postNestedUser.user.age: age 应当大于 18", cause.getMessage());
    }

    @Test
    @Order(7)
    void postNestedUserWithInvalidAddress() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        NestedUser user = new NestedUser(3, "name", 18, new NestedUser.Address((long) 1, "abcd"));

        MethodArgumentNotValidException resolvedException = ((MethodArgumentNotValidException) mockMvc
                .perform(post("/validation/nest").content(mapper.writeValueAsString(user))
                                                 .accept(MediaType.APPLICATION_JSON)
                                                 .locale(Locale.CHINA)
                                                 .contentType(MediaType.APPLICATION_JSON)).andReturn()
                .getResolvedException());
        List<String> collect = resolvedException.getBindingResult().getAllErrors().stream()
                                                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                                .collect(Collectors.toList());
        assertTrue(collect.contains("name 长度应该在 5~10 之间"));
    }

    @Test
    @Order(8)
    void postNestedUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        NestedUser user = new NestedUser(3, "user", 18, new NestedUser.Address((long) 1, "abcde"));
        String contentAsString = mockMvc.perform(post("/validation/nest").content(mapper.writeValueAsString(user))
                                                                         .accept(MediaType.APPLICATION_JSON)
                                                                         .contentType(MediaType.APPLICATION_JSON))
                                        .andReturn()
                                        .getResponse()
                                        .getContentAsString();
        String responseEntity = mapper.convertValue(contentAsString, String.class);
        assertEquals("success", responseEntity);
    }

    @Test
    @Order(9)
    void PostBatchUser() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        ValidUser user = new ValidUser((long) 4, "name1", 10);
        String contentAsString = mockMvc.perform(post("/validation/batch").content(mapper.writeValueAsString(Collections
                .singleton(user)))
                                                                          .accept(MediaType.APPLICATION_JSON)
                                                                          .contentType(MediaType.APPLICATION_JSON))
                                        .andReturn()
                                        .getResponse()
                                        .getContentAsString();
        String responseEntity = mapper.convertValue(contentAsString, String.class);
        assertEquals("success", responseEntity);
    }

    @Test
    @Order(10)
    void PostBatchUserWithInvalidName() {
        ObjectMapper mapper = new ObjectMapper();
        ValidUser user = new ValidUser((long) 4, "name", 10);

        NestedServletException exception = assertThrows(NestedServletException.class, () -> mockMvc
                .perform(post("/validation/batch").content(mapper.writeValueAsString(Collections.singletonList(user)))
                                                  .accept(MediaType.APPLICATION_JSON)
                                                  .locale(Locale.CHINA)
                                                  .contentType(MediaType.APPLICATION_JSON)));
        ConstraintViolationException cause = ((ConstraintViolationException) exception.getCause());
        assertEquals("postBatchUser.user[0].name: name 长度应该在 5~10 之间", cause.getMessage());
    }

    @Test
    @Order(11)
    void getUserWithInvalidId() {

        NestedServletException exception = assertThrows(NestedServletException.class, () -> mockMvc
                .perform(get("/validation/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .locale(Locale.CHINA)
                        .contentType(MediaType.APPLICATION_JSON)));
        ConstraintViolationException cause = ((ConstraintViolationException) exception.getCause());
        assertEquals("getUser.id: 最小不能小于3", cause.getMessage());
    }

    @Test
    @Order(12)
    void getUser() throws Exception {
        String contentAsString = mockMvc.perform(get("/validation/3")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                                        .andReturn()
                                        .getResponse()
                                        .getContentAsString();
        assertEquals("{\"id\":3,\"name\":\"user\",\"age\":18,\"address\":{\"id\":1,\"country\":\"abcde\"}}", contentAsString);
    }

    @Test
    @Order(13)
    void getUserByQueryWithInvalidId() {

        NestedServletException exception = assertThrows(NestedServletException.class, () -> mockMvc
                .perform(get("/validation/query?id=1")
                        .accept(MediaType.APPLICATION_JSON)
                        .locale(Locale.CHINA)
                        .contentType(MediaType.APPLICATION_JSON)));
        ConstraintViolationException cause = ((ConstraintViolationException) exception.getCause());
        assertEquals("getUserByQuery.id: 最小不能小于3", cause.getMessage());
    }

    @Test
    @Order(14)
    void testGetUserByQuery() throws Exception {
        String contentAsString = mockMvc.perform(get("/validation/query?id=3")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                                        .andReturn()
                                        .getResponse()
                                        .getContentAsString();
        assertEquals("{\"id\":3,\"name\":\"user\",\"age\":18,\"address\":{\"id\":1,\"country\":\"abcde\"}}", contentAsString);
    }

}