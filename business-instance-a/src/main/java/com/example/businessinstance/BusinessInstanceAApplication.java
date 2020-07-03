package com.example.businessinstance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class BusinessInstanceAApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessInstanceAApplication.class, args);
    }

    @RequestMapping("/service/name")
    String name() {
        return "business-instance-a";
    }
}
