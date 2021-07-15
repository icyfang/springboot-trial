package com.example.jpa.audit;

import com.example.jpa.querydsl.singleTable.UserPO;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * @author Hodur
 * @date 2021-01-06
 */
@Configuration
public class AuditorAwareForUser implements AuditorAware<UserPO> {

    @Override
    public Optional<UserPO> getCurrentAuditor() {
        //要使用的当前用户
        UserPO userPO = new UserPO();
        userPO.setId((long) 1);
        return Optional.of(userPO);
    }

}
