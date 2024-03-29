package com.example.springcontext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Hodur
 * @since 2021-05-28
 */
@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy(exposeProxy = true)
public class SpringContextApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringContextApplication.class, args);
    }

}
