package com.example.springmvc.swagger;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    UserController userController;

    private MockMvc mockMvc;

    @BeforeAll
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @Order(1)
    public void getUserList() throws Exception {
        assertUserList("[]");
    }

    private void assertUserList(String content) throws Exception {
        ResultActions perform = mockMvc.perform(get("/users/"));
        perform.andReturn().getResponse().setCharacterEncoding("UTF-8");
        perform.andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().string(equalTo(content)));
    }

    @Test
    @Order(2)
    void postUser() throws Exception {
        String content = "{\"id\":1,\"name\":\"测试大师\",\"age\":20}";
        mockMvc.perform(post("/users/")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
               .andExpect(content().string(equalTo("success")));

        assertUserList("[{\"id\":1,\"name\":\"测试大师\",\"age\":20}]");
    }

    @Test
    @Order(3)
    void putUser() throws Exception {
        mockMvc.perform(put("/users/{id}", 1)
                .content("{\"name\":\"测试终极大师\",\"age\":30}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(content().string(equalTo("success")));

        ResultActions perform = mockMvc.perform(get("/users/1"));
        perform.andReturn().getResponse().setCharacterEncoding("UTF-8");
        perform.andExpect(status().isOk())
               .andExpect(content().string(equalTo("{\"id\":1,\"name\":\"测试终极大师\",\"age\":30}")));
    }

    @Test
    @Order(4)
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/users/1"))
               .andDo(print())
               .andExpect(content().string(equalTo("success")));

        mockMvc.perform(get("/users/"))
               .andExpect(status().isOk())
               .andExpect(content().string(equalTo("[]")));
    }
}
