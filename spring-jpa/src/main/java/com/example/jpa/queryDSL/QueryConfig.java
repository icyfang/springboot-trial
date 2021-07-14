package com.example.jpa.queryDSL;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

/**
 * @author Hodur
 * @date 2021/7/14
 */
@Configuration
public class QueryConfig {

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManagerPrimary) {
        return new JPAQueryFactory(entityManagerPrimary);
    }

}
