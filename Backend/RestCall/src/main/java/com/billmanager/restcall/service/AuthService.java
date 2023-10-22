package com.billmanager.restcall.service;


import com.common.dto.*;
import com.billmanager.restcall.exception.AuthException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    RegistrationResponse registration(RegistrationRequest registrationRequest) throws AuthException;

    LoginResponse login(LoginRequest loginRequest, HttpServletResponse response) throws AuthException;

    LogoutReponse logout(HttpServletRequest request, HttpServletResponse response) throws AuthException, ServletException;
}
