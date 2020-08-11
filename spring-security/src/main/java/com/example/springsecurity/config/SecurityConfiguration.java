package com.example.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.header.HeaderWriter;

/**
 * @author Shanghong Cai
 * @create 2020-07-11 16:44
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    protected SecurityConfiguration() {
        super();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        configSecurityContext(http);
        configHeaders(http);
        configLogout(http);
        configureFormLogin(http);
        configExceptionHandler(http);

        HttpBasicConfigurer<HttpSecurity> httpSecurityHttpBasicConfigurer = http.httpBasic();
        RequestCacheConfigurer<HttpSecurity> requestCacheConfigurer = http.requestCache();
        ServletApiConfigurer<HttpSecurity> servletApiConfigurer = http.servletApi();
        AnonymousConfigurer<HttpSecurity> anonymous = http.anonymous();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private void configLogout(HttpSecurity http) throws Exception {
        LogoutConfigurer<HttpSecurity> logout = http.logout();
        logout.logoutUrl("/logout").logoutSuccessUrl("/login");
    }

    private void configExceptionHandler(HttpSecurity http) throws Exception {
        ExceptionHandlingConfigurer<HttpSecurity> exceptionHandlingConfigurer = http.exceptionHandling();
        AccessDeniedHandlerImpl accessDeniedHandler = new AccessDeniedHandlerImpl();
        accessDeniedHandler.setErrorPage("/errorPage");
        exceptionHandlingConfigurer.accessDeniedHandler(accessDeniedHandler);
    }

    private void configHeaders(HttpSecurity http) throws Exception {
        HeadersConfigurer<HttpSecurity> headers = http.headers();
        HeaderWriter writer = (request, response) -> {
            //...
        };
        headers.addHeaderWriter(writer);
        HeadersConfigurer<HttpSecurity>.CacheControlConfig cacheControlConfig = headers.cacheControl();
    }

    private void configSecurityContext(HttpSecurity http) throws Exception {
        SecurityContextConfigurer<HttpSecurity> securityContextConfigurer = http.securityContext();
        HttpSessionSecurityContextRepository r = new HttpSessionSecurityContextRepository();
        r.setAllowSessionCreation(true);
        securityContextConfigurer.securityContextRepository(r);
    }

    private void configureFormLogin(HttpSecurity http) throws Exception {
        FormLoginConfigurer<HttpSecurity> formLoginConfigurer = http.formLogin();
        // the login page
        formLoginConfigurer.loginPage("/login");
        // the login interface requested
        formLoginConfigurer.loginProcessingUrl("/login");
        // the page when authentication faield.
        formLoginConfigurer.failureUrl("/login?error");
        formLoginConfigurer.failureForwardUrl("/login");
        // the page when authentication success.
        formLoginConfigurer.successForwardUrl("/home");
        // the parameter name for username and password
        formLoginConfigurer.passwordParameter("password");
        formLoginConfigurer.usernameParameter("username");
        //todo
        formLoginConfigurer.authenticationDetailsSource(null);
        formLoginConfigurer.successHandler((request, response, authentication) -> System.out.println("success"));
        formLoginConfigurer.failureHandler((request, response, authentication) -> System.out.println("failed"));
    }

}