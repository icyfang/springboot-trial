package com.example.oauth2passwordauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author Hodur
 * @date 2020-07-23
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                 .userDetailsService(userDetailsService);
//                .tokenStore(jwtTokenStore())
//                .accessTokenConverter(jwtAccessTokenConverter());

    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.checkTokenAccess("isAuthenticated()");
//        oauthServer.tokenKeyAccess()
        System.out.println(oauthServer.getCheckTokenAccess());
        System.out.println(oauthServer.getTokenKeyAccess());

    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // use memory store
        clients.inMemory()
                // client id and secret
                .withClient("clientapp").secret("112233")
                // password pattern
                .authorizedGrantTypes("password")
                // authorization_code pattern
                .authorizedGrantTypes("authorization_code")
                .redirectUris("http://localhost:9090/redirect")
                // lient credentials
                .authorizedGrantTypes("client_credentials")
                // refresh token
                .authorizedGrantTypes("refresh_token")
                //scope
                .scopes("read_userinfo", "read_contacts")
                .accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(3600 * 4)

        // other client
//                .and().withClient()
        ;
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        // JWT 秘钥
        converter.setSigningKey("jwt_key");
        return converter;
    }

    @Bean
    public JwtTokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

}
