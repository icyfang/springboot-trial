package com.example.springmvc.jsr303;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Hodur
 * @date 2020-11-03
 */
public class CustomValidator implements ConstraintValidator<CustomCon, ValidUser> {

    @Override
    public boolean isValid(ValidUser value, ConstraintValidatorContext context) {
        return value.getName().equals("user");
    }

    @Override
    public void initialize(CustomCon constraintAnnotation) {
        String message = constraintAnnotation.message();
    }
}
