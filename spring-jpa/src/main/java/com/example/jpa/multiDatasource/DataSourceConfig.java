package com.example.jpa.multiDatasource;

import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

//    another method to config datasource

//    @Bean(name = "primaryDataSource")
//    @Qualifier("primaryDataSource")
//    @Primary
//    @ConfigurationProperties(prefix = "spring.primary.datasource")
//    public DataSource primaryDataSource() {
//        return primaryDataSourceProperties().initializeDataSourceBuilder().build();
//    }
//
//    @Primary
//    @Bean
//    @ConfigurationProperties(prefix = "spring.primary.datasource")
//    public DataSourceProperties primaryDataSourceProperties(){
//        return new DataSourceProperties();
//    }


}
