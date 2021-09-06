package com.example.jpa.transactional;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * <p>
 * jpa does not support propagation_nested, so use jdbcTemplate for example
 * </p>
 *
 * @author Hodur
 * @date 2021/9/3
 */
@Configuration
public class JdbcConfig {

    @Bean(name = "jdbcTransactionManager")
    public PlatformTransactionManager transactionManagerPrimary(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public DataSource dataSource(DataSourceProperties builder) {
        return builder.initializeDataSourceBuilder().build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
