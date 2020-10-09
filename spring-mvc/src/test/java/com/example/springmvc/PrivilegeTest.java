package com.example.springmvc;

import com.example.springmvc.aop.exception.PersonHandler;
import com.example.springmvc.aop.privilege.Privilege;
import com.example.springmvc.aop.privilege.PrivilegeAspect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * @author Shanghong Cai
 * @create 2020-07-20 21:46
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PrivilegeTest {

    @Autowired
    private PrivilegeAspect privilegeAspect;

    @Autowired
    private PersonHandler handler;

    MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(handler)
                                 .build();
    }

    @Test
    public void test() throws Exception {

        privilegeAspect.getPrivileges()
                       .addAll(Arrays.asList(new Privilege("getPerson"), new Privilege("updatePerson")));
        System.out.println(mockMvc.perform(get("/person/update"))
                                  .andReturn()
                                  .getResponse()
                                  .getContentAsString());
        System.out.println(mockMvc.perform(get("/person/save"))
                                  .andReturn()
                                  .getResponse()
                                  .getContentAsString());
    }
}
