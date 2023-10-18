package com.billmanager.restcall.dto;


import com.billmanager.restcall.validator.LoginValid;

import java.lang.annotation.Annotation;

@LoginValid
public record LoginRequest(
        String name,
        String password,
        String fingerId
) implements Annotation {
    @Override
    public Class<? extends Annotation> annotationType() {
        return LoginRequest.class;
    }
}
