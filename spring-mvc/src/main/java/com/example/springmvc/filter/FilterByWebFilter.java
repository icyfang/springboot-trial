package com.example.springmvc.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author Shanghong Cai
 * @since 2020-08-05
 */
@WebFilter(urlPatterns = "/advice/v1")
public class FilterByWebFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("start FilterByWebFilter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        System.out.println("enter FilterByWebFilter");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("exit FilterByWebFilter");
    }
}
