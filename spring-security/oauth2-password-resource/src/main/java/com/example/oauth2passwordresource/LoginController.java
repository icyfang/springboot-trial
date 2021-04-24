package com.example.oauth2passwordresource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hodur
 * @create 2020-07-26 16:28
 */
@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    private ResourceOwnerPasswordResourceDetails resourceDetails;

    @Autowired
    private AuthorizationCodeResourceDetails authorizationCodeResourceDetails;

    @Autowired
    private ClientCredentialsResourceDetails clientCredentialsResourceDetails;

    @PostMapping("/login")
    public OAuth2AccessToken login(@RequestParam("username") String username,
                                   @RequestParam("password") String password) {
        resourceDetails.setUsername(username);
        resourceDetails.setPassword(password);

        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails);
        restTemplate.setAccessTokenProvider(new ResourceOwnerPasswordAccessTokenProvider());

        return restTemplate.getAccessToken();
    }

    @RequestMapping("/hello")
    public String hello() {
        return "world";
    }

    @GetMapping("/redirect")
    public OAuth2AccessToken redirect(@RequestParam("code") String code) {

        // 创建 OAuth2RestTemplate 对象
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(authorizationCodeResourceDetails);
        // <1> 设置 code
        restTemplate.getOAuth2ClientContext().getAccessTokenRequest().setAuthorizationCode(code);
        // <2> 通过这个方式，设置 redirect_uri 参数
        restTemplate.getOAuth2ClientContext().getAccessTokenRequest()
                .setPreservedState("http://localhost:9090/redirect");
        restTemplate.setAccessTokenProvider(new AuthorizationCodeAccessTokenProvider());
        // 获取访问令牌
        return restTemplate.getAccessToken();
    }

    @PostMapping("/client")
    public OAuth2AccessToken login() {
        // 创建 OAuth2RestTemplate 对象
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(clientCredentialsResourceDetails);
        restTemplate.setAccessTokenProvider(new ClientCredentialsAccessTokenProvider());
        // 获取访问令牌
        return restTemplate.getAccessToken();
    }

    @RequestMapping("/info")
    public Authentication info(Authentication authentication) {
        return authentication;
    }
}
