package com.billmanager.restcall.controller;

import com.billmanager.restcall.dto.LoginRequest;
import com.billmanager.restcall.dto.LoginResponse;
import com.billmanager.restcall.dto.RegistrationRequest;
import com.billmanager.restcall.dto.RegistrationResponse;
import com.billmanager.restcall.service.AuthService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Log4j2
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping
    private RegistrationResponse registration(RegistrationRequest registrationRequest) {
        return authService.registration();
    }

    @GetMapping
    private LoginResponse login(LoginRequest loginRequest) {
        return authService.login();
    }

}
