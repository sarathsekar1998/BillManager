package com.billmanager.restcall.service.impl;

import com.billmanager.restcall.dao.User;
import com.common.dto.*;
import com.billmanager.restcall.exception.AuthException;
import com.billmanager.restcall.repository.UserRepository;
import com.billmanager.restcall.service.AuthService;
import com.common.util.ErrorMsg;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.common.util.Static.TOKEN_KEY;

@Service
@Log4j2
public class Authserviceimpl implements AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager manager;

    @Autowired
    List<String> invalidToken;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtAuthentication jwtAuthentication;

    public RegistrationResponse registration(RegistrationRequest registrationRequest) throws AuthException {
        try {
            User user = null;
            RegistrationResponse registrationResponse = null;
            user = User.builder()
                    .name(registrationRequest.name())
                    .mobile(registrationRequest.mobileNo())
                    .password(encoder.encode(registrationRequest.password()))
                    .fingerId(registrationRequest.fingerId())
                    .createdOn(LocalDateTime.now()).build();
            user = userRepository.save(user);
            if (!Objects.isNull(user.getId())) {
                registrationResponse = new RegistrationResponse("successfully registered user : " + user.getName());
            } else {
                registrationResponse = new RegistrationResponse("unfortunate failed.");
            }
            return registrationResponse;
        } catch (Exception e) {
            log.error("registration error " + e);
            if (e instanceof DuplicateKeyException) throw new AuthException(ErrorMsg.USERNAME_EXIST);
            else throw new AuthException(ErrorMsg.FAILED);
        }
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest, HttpServletResponse response) throws AuthException {
        Authentication authenticate = null;
        Optional<User> user = null;
        try{
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
            User user1 = userRepository.findByName(authenticate.getName());
            user1.setAccesstoken(jwtAuthentication.generateAccessToken(user1.getName()));
            user1.setRefreshToken(jwtAuthentication.generateRefreshToken(user1.getName()));
            user1.setDevice(loginRequest.deviceType());
            user1.setStatus(1);
            user1 = userRepository.save(user1);
            Cookie cookie = new Cookie("x-bill-token",user1.getAccesstoken());
            cookie.setMaxAge((int) jwtAuthentication.ACCESS_TOKEN_EXPIRATION);
            cookie.setPath("/");
            Cookie cookie1 = new Cookie("x-bill-refresh",user1.getRefreshToken());
            cookie1.setMaxAge(3600*24*30);
            cookie1.setPath("/");
            response.addCookie(cookie);
            response.addCookie(cookie1);
            return new LoginResponse(user1.getName(), user1.getMobile());
        }catch (Exception e){
            log.error("login error "+ e);
            if(e instanceof BadCredentialsException) throw new AuthException(ErrorMsg.BADCREDENTIAL);
            else if (e instanceof AccountExpiredException) throw new AuthException(ErrorMsg.ALREADY_LOGGED);
            else throw new AuthException(ErrorMsg.FAILED);
        }
    }

    @Override
    public LogoutReponse logout(HttpServletRequest request, HttpServletResponse response) throws AuthException {
        try {
            String token = request.getHeader(TOKEN_KEY).substring(7);
            String name = jwtAuthentication.getUsernameFromToken(token);
            User user = userRepository.findByName(name);
            user.setStatus(0);
            user.setAccesstoken(null);
            user.setRefreshToken(null);
            user.setDevice(null);
            Cookie cookie = new Cookie("x-bill-token","");
            cookie.setMaxAge(0);
            cookie.setPath("/");
            Cookie cookie1 = new Cookie("x-bill-refresh","");
            cookie.setMaxAge(0);
            cookie1.setPath("/");
            response.addCookie(cookie);
            response.addCookie(cookie1);
            userRepository.save(user);
            invalidToken.add(token);
            return new LogoutReponse("succesfully logged out");
        }catch (Exception e){
            throw new AuthException(ErrorMsg.FAILED);
        }
    }
}
