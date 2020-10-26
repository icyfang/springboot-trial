package com.example.springcontext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringContextApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringContextApplication.class, args);
    }

}
