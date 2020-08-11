package com.example.springmvc.advice;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Shanghong Cai
 * @since 2020-08-10
 */
@Data
@AllArgsConstructor
public class ResponseEntity<T> {

    private String code;
    private String message;
    private T body;

    public static <T> ResponseEntity<T> of(String code, String msg, T t) {
        return new ResponseEntity<>(code, msg, t);
    }
}
