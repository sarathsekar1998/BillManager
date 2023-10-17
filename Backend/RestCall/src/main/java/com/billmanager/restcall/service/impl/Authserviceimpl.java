package com.billmanager.restcall.service.impl;

import com.billmanager.restcall.dto.LoginResponse;
import com.billmanager.restcall.dto.RegistrationResponse;
import com.billmanager.restcall.exception.AuthException;
import com.billmanager.restcall.service.AuthService;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
public class Authserviceimpl implements AuthService {
    public RegistrationResponse registration() throws AuthException {
        return new RegistrationResponse();
    }

    @Override
    public LoginResponse login() throws AuthException {
        return new LoginResponse();
    }
}
