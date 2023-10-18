package com.billmanager.restcall.service.impl;

import com.billmanager.restcall.dao.User;
import com.billmanager.restcall.dto.LoginRequest;
import com.billmanager.restcall.dto.LoginResponse;
import com.billmanager.restcall.dto.RegistrationRequest;
import com.billmanager.restcall.dto.RegistrationResponse;
import com.billmanager.restcall.exception.AuthException;
import com.billmanager.restcall.repository.UserRepository;
import com.billmanager.restcall.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class Authserviceimpl implements AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager manager;

    @Autowired
    JwtAuthentication jwtAuthentication;

    public RegistrationResponse registration(RegistrationRequest registrationRequest) throws AuthException {
        User user = null;
        RegistrationResponse registrationResponse = null;
        user = User.builder().name(registrationRequest.name()).mobile(registrationRequest.mobileNo()).password(registrationRequest.password()).fingerId(registrationRequest.fingerId()).build();
        user = userRepository.save(user);
        if (!Objects.isNull(user.getId())) {
            registrationResponse = new RegistrationResponse("successfully registered user : " + user.getName());
        } else {
            registrationResponse = new RegistrationResponse("unfortunate failed.");
        }
        return registrationResponse;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws AuthException {
        SecurityContext context = null;
        Authentication authenticate = null;
        Optional<User> user = null;
        if (!Objects.isNull(loginRequest.name())) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.name(), loginRequest.password());
            authenticate = manager.authenticate(usernamePasswordAuthenticationToken);

        } else {
            user = userRepository.findByFingerId(loginRequest.fingerId());
            if (user.isPresent()) {
                String name = user.get().getName();
                String password = user.get().getPassword();
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(name, password, null);
                authenticate = manager.authenticate(usernamePasswordAuthenticationToken);
            }
        }
        context = SecurityContextHolder.getContext();
        context.setAuthentication(authenticate);
        User user1 = userRepository.findByName(authenticate.getName());
        user1.setAccesstoken(jwtAuthentication.generateAccessToken(user1.getName()));
        user1.setRefreshToken(jwtAuthentication.generateRefreshToken(user1.getName()));
        user1.setDevice("Mobile");
        user1.setStatus(1);
        user1 = userRepository.save(user1);
        return new LoginResponse(user1.getName(),user1.getMobile(),user1.getAccesstoken(),user1.getRefreshToken());
    }
}
