package com.example.springmvc.swagger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserControllerTest {

    @Autowired
    UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void crudTest()throws Exception{
        getUserList("[]");
        postUser();
        getUserList("[{\"id\":1,\"name\":\"测试大师\",\"age\":20}]");
        putUser();
        getUser("{\"id\":1,\"name\":\"测试终极大师\",\"age\":30}");
        deleteUser();
        getUserList("[]");
    }

    void getUserList(String expect) throws Exception {
        RequestBuilder request = get("/users/");
        mockMvc.perform(request)
               .andExpect(status().isOk())
               .andExpect(content().string(equalTo(expect)));
    }

    void postUser() throws Exception {
        RequestBuilder request = post("/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"name\":\"测试大师\",\"age\":20}");
        mockMvc.perform(request)
               .andExpect(content().string(equalTo("success")));
    }

    void getUser(String expect) throws Exception {
        RequestBuilder request = get("/users/1");
        ResultActions perform = mockMvc.perform(request);
        perform.andReturn().getResponse().setCharacterEncoding("UTF-8");
        perform.andExpect(status().isOk())
                .andExpect(content().string(equalTo(expect)));
    }

    void putUser() throws Exception {
        ResultActions perform = mockMvc.perform(post("/users/")
                .content("{\"name\":\"测试终极大师\",\"age\":30}")
                // todo test if param work for path parameter
                .param("id","1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));
        perform.andReturn().getResponse().setCharacterEncoding("UTF-8");
        perform.andExpect(content().string(equalTo("success")));
    }

    void deleteUser() throws Exception {
        RequestBuilder request = delete("/users/1");
        mockMvc.perform(request)
               .andExpect(content().string(equalTo("success")));
    }
}
