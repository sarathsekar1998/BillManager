package com.billmanager.restcall.service;


import com.billmanager.restcall.dto.LoginRequest;
import com.billmanager.restcall.dto.LoginResponse;
import com.billmanager.restcall.dto.RegistrationRequest;
import com.billmanager.restcall.dto.RegistrationResponse;
import com.billmanager.restcall.exception.AuthException;

public interface AuthService {
    RegistrationResponse registration(RegistrationRequest registrationRequest) throws AuthException;

    LoginResponse login(LoginRequest loginRequest) throws AuthException;
}
