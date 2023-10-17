package com.billmanager.restcall.service;


import com.billmanager.restcall.dto.LoginResponse;
import com.billmanager.restcall.dto.RegistrationResponse;
import com.billmanager.restcall.exception.AuthException;

public interface AuthService {
    RegistrationResponse registration() throws AuthException;

    LoginResponse login() throws AuthException;
}
