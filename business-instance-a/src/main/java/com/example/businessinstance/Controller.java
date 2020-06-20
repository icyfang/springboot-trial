package com.example.businessinstance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Controller {
    String serviceNmae = "BUSINESS-INSTANCE";

    @RequestMapping("/getName")
    public String getName() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://" + serviceNmae + "/name", String.class);
        return forEntity.getBody();
    }

    @Autowired
    private RestTemplate restTemplate;
}
