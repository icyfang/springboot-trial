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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ValidatorControllerTest extends MvcTestHelper {

    @Autowired
    private ValidatorController validUserController;

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
    @Order(15)
    void postNestedUser() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        NestedUser user = new NestedUser(5, "name", 10, new NestedUser.Address((long) 1, "abcde"));
        String contentAsString = getResponseContent("/validator/", user);
        String responseEntity = mapper.convertValue(contentAsString, String.class);
        assertEquals("success", responseEntity);
    }

    @Test
    @Order(16)
    void postNestedUserWithInvalidName() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        NestedUser user = new NestedUser(5, "name", 10, new NestedUser.Address((long) 1, "abcd"));
        String contentAsString = getResponseContent("/validator/", user);
        String responseEntity = mapper.convertValue(contentAsString, String.class);
        assertEquals("国家名长度应该在 5~10 之间", responseEntity);
    }

    @Test
    @Order(17)
    void postNestedUserWithinGroupWithInvalidName() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        NestedUser user = new NestedUser(6, "name", 10, new NestedUser.Address((long) 1, "abcd"));
        String contentAsString = getResponseContent("/validator/group", user);
        String responseEntity = mapper.convertValue(contentAsString, String.class);
        assertEquals("age 应当大于 18", responseEntity);
    }

    @Test
    @Order(18)
    void postNestedUserWithinGroup() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        NestedUser user = new NestedUser(6, "name", 20, new NestedUser.Address((long) 1, "abcd"));
        String contentAsString = getResponseContent("/validator/group", user);
        String responseEntity = mapper.convertValue(contentAsString, String.class);
        assertEquals("success", responseEntity);
    }

}
