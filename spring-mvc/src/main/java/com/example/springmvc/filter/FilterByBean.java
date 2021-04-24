package com.example.springmvc.filter;

import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author Hodur
 * @since 2020-08-05
 */
@Component
public class FilterByBean implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("start FilterByBean");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        System.out.println("enter FilterByBean");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("exit FilterByBean");
    }
}
