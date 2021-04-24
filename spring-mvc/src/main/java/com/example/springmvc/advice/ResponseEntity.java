package com.example.springmvc.advice;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Hodur
 * @since 2020-08-10
 */
@Data
@AllArgsConstructor
public class ResponseEntity<T> {

    private int code;
    private String message;
    private T body;

    public static <T> ResponseEntity<T> of(int code, String msg, T t) {
        return new ResponseEntity<>(code, msg, t);
    }
}
