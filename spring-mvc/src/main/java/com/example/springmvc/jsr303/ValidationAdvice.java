package com.example.springmvc.jsr303;

import com.example.springmvc.advice.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hodur
 * @date 2020-11-03
 */
@RestControllerAdvice
public class ValidationAdvice {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> collect = bindingResult.getFieldErrors().stream()
                                            .map(i -> i.getField() + ":" + i.getDefaultMessage())
                                            .collect(Collectors.toList());
        return ResponseEntity.of(400, "MethodArgumentNotValidException", collect);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<String>> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> collect = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                                 .collect(Collectors.toList());
        return ResponseEntity.of(400, "ConstraintViolationException", collect);
    }

}
