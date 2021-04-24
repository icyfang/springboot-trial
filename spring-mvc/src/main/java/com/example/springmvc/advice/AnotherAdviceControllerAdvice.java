package com.example.springmvc.advice;

import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Hodur
 * @since 2020-08-10
 */
@RestControllerAdvice(assignableTypes = AnotherAdviceController.class)
public class AnotherAdviceControllerAdvice {

    @InitBinder
    public void initWebBinder(WebDataBinder binder) {
        binder.addCustomFormatter(new DateFormatter("yyyy-MM-dd hh:mm:ss"));
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String customExceptionHandler(Exception e) {
        return e.getMessage() + "" + e.getLocalizedMessage();
    }
}
