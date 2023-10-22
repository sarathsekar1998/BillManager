package com.common.dto;


import com.common.validator.LoginValid;

import java.lang.annotation.Annotation;

@LoginValid
public record LoginRequest(
        String name,
        String password,
        String deviceType,
        String fingerId
) implements Annotation {
    @Override
    public Class<? extends Annotation> annotationType() {
        return LoginRequest.class;
    }
}
