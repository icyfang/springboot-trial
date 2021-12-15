package com.example.springretry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @author Hodur
 * @date 2020/12/15
 */
@SpringBootApplication
@EnableRetry
public class SpringRetryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRetryApplication.class, args);
    }

}
