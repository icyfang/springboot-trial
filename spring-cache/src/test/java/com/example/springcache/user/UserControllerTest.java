package com.example.springcache.user;

import com.example.basic.model.User;
import com.example.springcache.controller.UserController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private MockMvc mockMvc;

    @BeforeAll
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @Order(1)
    public void getUser() throws Exception {
        assertUser("");
    }

    private void assertUser(String content) throws Exception {
        ResultActions perform = mockMvc.perform(get("/user/{id}", 1));
        perform.andReturn().getResponse().setCharacterEncoding("UTF-8");
        perform.andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().string(equalTo(content)));
    }

    @Test
    @Order(2)
    void postUser() throws Exception {
        User u = new User((long) 1, "user1", 20);
        String content = new ObjectMapper().writeValueAsString(u);

        ResultActions perform = mockMvc.perform(post("/user")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));
        perform.andReturn().getResponse().setCharacterEncoding("UTF-8");
        perform.andExpect(content().string(equalTo(content)));
        assertUser(content);
        String o = ((String) redisTemplate.opsForValue().get("user::1"));
        System.out.println(o);
        Assertions.assertEquals(u, o);
    }

    @Test
    @Order(3)
    void putUser() throws Exception {
        User u = new User((long) 1, "user2", 30);
        String content = new ObjectMapper().writeValueAsString(u);

        mockMvc.perform(put("/user/{id}", 1)
                .content(content)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(content().string(equalTo("true")));
        assertUser(content);
        User o = ((User) redisTemplate.opsForValue().get("user::1"));
        Assertions.assertNull(o);
    }

    @Test
    @Order(4)
    void deleteUser() throws Exception {

        mockMvc.perform(delete("/user/{id}", 1))
               .andDo(print())
               .andExpect(content().string(equalTo("true")));

        assertUser("");
        User o = ((User) redisTemplate.opsForValue().get("user::1"));
        Assertions.assertNull(o);
    }
}
