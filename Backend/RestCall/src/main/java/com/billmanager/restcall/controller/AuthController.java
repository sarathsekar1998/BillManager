package com.billmanager.restcall.controller;

import com.common.dto.*;
import com.billmanager.restcall.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.common.util.Static.*;

@RestController
@RequestMapping(PATH)
@Log4j2
public class AuthController {

    @Autowired
    public AuthService authService;

    @PostMapping(value = REGISTRATION, consumes = MediaType.APPLICATION_JSON_VALUE)
    private RegistrationResponse registration(@RequestBody RegistrationRequest registrationRequest) {
        return authService.registration(registrationRequest);
    }

    @PostMapping(value = LOGIN, consumes = MediaType.APPLICATION_JSON_VALUE)
    private LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        return authService.login(loginRequest,response);
    }

    @PostMapping(value = LOGOUT,consumes = MediaType.ALL_VALUE)
    private LogoutReponse logout(HttpServletRequest request,HttpServletResponse response) throws ServletException {
        return authService.logout(request,response);
    }

}
