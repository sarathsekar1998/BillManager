package com.common.validator;

import com.common.dto.LoginRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class LoginValidator implements ConstraintValidator<LoginRequest,Object> {

    @Override
    public void initialize(LoginRequest constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        LoginRequest request = (LoginRequest) o;
        constraintValidatorContext.disableDefaultConstraintViolation();
        if(request.name() == null && request.fingerId() != null){
            constraintValidatorContext.buildConstraintViolationWithTemplate("fingerId is empty.");
        } else if (request.name()!=null && request.password() !=null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("name and password is empty.");
        }
        return false;
    }
}
