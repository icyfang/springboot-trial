package com.example.springmvc.advice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author Hodur
 * @date 2020-08-10
 */
@RestController
@RequestMapping("/advice/v2")
public class AnotherAdviceController {

    @GetMapping("/exception")
    public String getException() throws Exception {
        throw new Exception("Exception occur");
    }

    @GetMapping("/dateFormat")
    public String getDateFormat(Date date) {
        return date.toString();
    }
}
