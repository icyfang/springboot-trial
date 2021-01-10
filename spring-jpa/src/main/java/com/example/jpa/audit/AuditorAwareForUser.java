package com.example.jpa.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Shanghong Cai
 * @since 2021-01-06
 */
@Component
public class AuditorAwareForUser implements AuditorAware<User> {

    @Override
    public Optional<User> getCurrentAuditor() {
        //要使用的当前用户
        User user = new User();
        user.setId((long) 1);
        return Optional.of(user);
    }

}
