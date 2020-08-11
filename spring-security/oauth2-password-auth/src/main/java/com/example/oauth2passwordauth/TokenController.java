package com.example.oauth2passwordauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Shanghong Cai
 * @since 2020-07-30
 */
@RestController
public class TokenController {

    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @DeleteMapping("/token")
    public boolean deleteToken(@RequestParam("token") String token) {
        return consumerTokenServices.revokeToken(token);
    }
}
