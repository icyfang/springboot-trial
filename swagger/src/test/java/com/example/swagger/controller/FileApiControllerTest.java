package com.example.swagger.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileApiControllerTest {

    private MockMvc mockMvc;

    @Autowired
    FileApiController fileApiController;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(fileApiController).build();
    }

    @Test
    void downloadFile() {
    }

    @Test
    public void testUpload() throws Exception {

        FileInputStream fis = new FileInputStream(ResourceUtils.getFile("classpath:Book1.xlsx"));
        MockMultipartFile multipartFile = new MockMultipartFile("file", fis);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/product/overrideRateFile").file(multipartFile)
                                              .accept(MediaType.APPLICATION_JSON)
                                              .contentType(MediaType.MULTIPART_FORM_DATA)
        ).andDo(print());
    }
}
