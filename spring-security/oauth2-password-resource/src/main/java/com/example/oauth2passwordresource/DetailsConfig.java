package com.example.oauth2passwordresource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

/**
 * @author Hodur
 * @date 2020-07-27 00:12
 */
@Configuration
public class DetailsConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.security.oauth2.client")
    public ResourceOwnerPasswordResourceDetails passwordResourceDetails() {
        return new ResourceOwnerPasswordResourceDetails();
    }

    @Bean
    @ConfigurationProperties("spring.security.oauth2.client")
    public AuthorizationCodeResourceDetails authorizationCodeResourceDetails() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("spring.security.oauth2.client")
    public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
        return new ClientCredentialsResourceDetails();
    }

}
