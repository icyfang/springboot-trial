package com.example.springmvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author Hodur
 * @date 2021/12/8
 */
public abstract class MvcTestHelper {

    protected abstract MockMvc getMockMvc();

    public String getResponseContent(String s) throws Exception {
        return performGetRequest(s)
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    public String getResponseContent(String path, Object contentObj) throws Exception {

        return performPostRequest(path, contentObj)
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    public ResultActions performRequest(MockHttpServletRequestBuilder requestBuilder) throws Exception {
        return getMockMvc().perform(requestBuilder
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .locale(Locale.CHINA));
    }

    public ResultActions performPostRequest(String path, Object contentObj) throws Exception {
        MockHttpServletRequestBuilder performRequest =
                post(path).content(new ObjectMapper().writeValueAsString(contentObj));
        return performRequest(performRequest);
    }

    public ResultActions performGetRequest(String path) throws Exception {
        return performRequest(get(path));
    }

    public <T extends Throwable> T responseException(String path, Object obj, Class<T> t) {
        return assertThrows(t, () -> performPostRequest(path, obj));
    }
}
