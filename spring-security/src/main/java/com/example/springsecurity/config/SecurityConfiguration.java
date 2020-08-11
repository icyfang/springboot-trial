package com.example.springsecurity.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AnonymousConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.config.annotation.web.configurers.SecurityContextConfigurer;
import org.springframework.security.config.annotation.web.configurers.ServletApiConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.web.accept.ContentNegotiationStrategy;

/**
 * @author: Shanghong Cai
 * @descirption: security configuration
 * @create: 2020-07-11 16:44
 */
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    protected SecurityConfiguration() {
        super();
    }

    protected SecurityConfiguration(boolean disableDefaults) {
        super(disableDefaults);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }

    @Override
    public void init(WebSecurity web) throws Exception {
        super.init(web);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
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
        logout.logoutUrl("/logout").logoutSuccessUrl("/login").permitAll();
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
        formLoginConfigurer.permitAll();
    }

    @Override

    public void setApplicationContext(ApplicationContext context) {
        super.setApplicationContext(context);
    }

    @Override
    public void setTrustResolver(AuthenticationTrustResolver trustResolver) {
        super.setTrustResolver(trustResolver);
    }

    @Override
    public void setContentNegotationStrategy(ContentNegotiationStrategy contentNegotiationStrategy) {
        super.setContentNegotationStrategy(contentNegotiationStrategy);
    }

    @Override
    public void setObjectPostProcessor(ObjectPostProcessor<Object> objectPostProcessor) {
        super.setObjectPostProcessor(objectPostProcessor);
    }

    @Override
    public void setAuthenticationConfiguration(AuthenticationConfiguration authenticationConfiguration) {
        super.setAuthenticationConfiguration(authenticationConfiguration);
    }
}
