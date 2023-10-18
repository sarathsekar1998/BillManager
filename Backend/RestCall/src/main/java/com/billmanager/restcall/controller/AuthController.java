package com.billmanager.restcall.controller;

import com.billmanager.restcall.dto.LoginRequest;
import com.billmanager.restcall.dto.LoginResponse;
import com.billmanager.restcall.dto.RegistrationRequest;
import com.billmanager.restcall.dto.RegistrationResponse;
import com.billmanager.restcall.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.billmanager.restcall.util.Static.*;

@RestController
@RequestMapping(PATH)
@Log4j2
public class AuthController {

    @Autowired
    AuthService authService;
    @PostMapping(value = REGISTRATION,consumes = MediaType.APPLICATION_JSON_VALUE)
    private RegistrationResponse registration(@Valid RegistrationRequest registrationRequest) {
        return authService.registration(registrationRequest);
    }
    @GetMapping(value = LOGIN,consumes = MediaType.APPLICATION_JSON_VALUE)
    private LoginResponse login(LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

}
