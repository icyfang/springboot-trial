package com.example.springcontext.initializer;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author Hodur
 * @date 2020-08-05
 */
@Slf4j
public class MyFilter implements Filter {



    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("start");
        log.error("start");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        System.out.println("filter");
        log.error("filter");
        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {

    }
}
