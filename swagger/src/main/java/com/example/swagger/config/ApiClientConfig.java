package com.example.swagger.config;

import com.example.swagger.feign.ApiClient;
import com.example.swagger.feign.api.UserApi;
import feign.Feign;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApiClientConfig {

    @Bean
    public UserApi feignUserApi(){
        return apiClient().buildClient(UserApi.class);
    }

    @Bean
    public RestTemplate restTemplate() {
        AccessTokenRequest atr = new DefaultAccessTokenRequest();
        return new OAuth2RestTemplate(agentDataServiceApigeeCredentials(), new DefaultOAuth2ClientContext(atr));
    }

    // to config oauth2
    @Bean
    public ApiClient apiClient() {
        ApiClient apiClient = new ApiClient();
        Feign.Builder feignBuilder = apiClient.getFeignBuilder();
        feignBuilder.requestInterceptor(new FeignOauth2RequestInterceptor(true, "oauth2",
                new DefaultOAuth2ClientContext(), agentDataServiceApigeeCredentials()));
        return apiClient;
    }

    @Bean
    @ConfigurationProperties(prefix = "oauth2")
    public ClientCredentialsResourceDetails agentDataServiceApigeeCredentials() {
        return new ClientCredentialsResourceDetails();
    }

    @Slf4j
    public static class FeignOauth2RequestInterceptor extends OAuth2FeignRequestInterceptor {

        private boolean open;
        private final String serviceName;
        private static final int RETRY = 3;
        private static final String FORM_ENGINE_REQUEST_PREFIX = "/forms";

        public FeignOauth2RequestInterceptor(boolean open, String serviceName, OAuth2ClientContext oAuth2ClientContext,
                                             OAuth2ProtectedResourceDetails resource) {
            super(oAuth2ClientContext, resource);
            this.serviceName = serviceName;
            this.open = open;
        }

        @Override
        public void apply(RequestTemplate template) {
            if (!open) {
                return;
            }

            String url = template.url();

            // get agent data service apigee oauth2 token
            int retry = 0;
            while (!applyAuth(template) && retry < RETRY) {
                retry++;
            }

        }

        private boolean applyAuth(RequestTemplate template) {
            try {
                super.apply(template);
                return true;
            } catch (OAuth2AccessDeniedException e) {
                log.error("cpdts data service apigee OAuth2 token access denied, will do one more retry");
                return false;
            }
        }

    }
}
