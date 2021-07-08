package com.example.jpa.audit;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * @author Hodur
 * @date 2021-01-06
 */
@Configuration
public class AuditorAwareForUser implements AuditorAware<User> {

    @Override
    public Optional<User> getCurrentAuditor() {
        //要使用的当前用户
        User user = new User();
        user.setId((long) 1);
        return Optional.of(user);
    }

}
