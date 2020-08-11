package com.example.springmvc.advice;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Shanghong Cai
 * @since 2020-08-10
 */
@Data
@AllArgsConstructor
public class ExampleEntity {

    private String code;
    private String name;
}
