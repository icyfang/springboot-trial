package com.example.springmvc.jsr303;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/validator")
@Validated
public class ValidatorController {

    @Autowired
    private Validator validator;

    @PostMapping(value = "/", produces = "application/json;charset=UTF-8")
    public String postNestedUser(@RequestBody NestedUser user) {
        Set<ConstraintViolation<NestedUser>> validate = validator.validate(user);
        if (validate.isEmpty()) {
            return "success";
        } else {
            return validate.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        }
    }

    @PostMapping(value = "/group", produces = "application/json;charset=UTF-8")
    public String postNestedUserWithinGroup(@RequestBody NestedUser user) {
        Set<ConstraintViolation<NestedUser>> validate = validator.validate(user, ValidUser.Single.class);
        if (validate.isEmpty()) {
            return "success";
        } else {
            return validate.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        }
    }

}