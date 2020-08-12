package com.example.springmvc.config;

import com.example.springmvc.asyncIntercptor.AsyncTestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    //访问 http://127.0.0.1:8188/swagger-ui.html 时，swagger-ui.html 相关的所有前端静态文件都在 springfox-swagger-ui-2.6.1.jar 里面。
    //Spring Boot 自动配置本身不会自动把 /swagger-ui.html 这个路径映射到对应的目录 META-INF/resources/ 下面。需要加上这个映射。
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                                     Object handler) throws Exception {
                System.out.println("enter HandlerInterceptor");
                return true;
            }
        }).addPathPatterns("/advice/v1/*").order(10);
        registry.addInterceptor(new AsyncTestInterceptor()).addPathPatterns("/async/v1/*").order(1);
    }
}
