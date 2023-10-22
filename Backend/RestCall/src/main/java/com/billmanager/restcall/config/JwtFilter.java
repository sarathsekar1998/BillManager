package com.billmanager.restcall.config;

import com.billmanager.restcall.exception.AuthException;
import com.billmanager.restcall.service.impl.JwtAuthentication;
import com.common.util.ErrorMsg;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;


import static com.common.util.Static.TOKEN_KEY;

@Component
@Log4j2
public class JwtFilter extends OncePerRequestFilter {

    JwtAuthentication jwtAuthentication;
    List<String> invalidoken;
    ObjectMapper objectMapper;

    private static final String[] allowedPath = new String[]{"/auth/registration,/auth/login"};

    public JwtFilter(JwtAuthentication jwtAuthentication, List<String> tokens) {
        this.jwtAuthentication = jwtAuthentication;
        this.invalidoken = tokens;
        this.objectMapper = new ObjectMapper();
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if(!isAllowed(request.getRequestURI())) {
                String token = extractToken(request.getHeader(TOKEN_KEY));
                if (token.isEmpty())
                    throw new AuthException(ErrorMsg.TOKEN_EMPTY);
                else if (!jwtAuthentication.validateToken(token))
                    throw new AuthException(ErrorMsg.INVALID_TOKEN);
                else {
                    if(invalidoken.contains(token))
                        throw new AuthException(ErrorMsg.ALREADY_LOGGED_OUT);
                    String username = jwtAuthentication.getUsernameFromToken(token);
                    Authentication authentication1 = new UsernamePasswordAuthenticationToken(username, null, null);
                    SecurityContextHolder.getContext().setAuthentication(authentication1);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(400);
            response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            if(e instanceof AuthException) setErrorResponse(response, ((AuthException) e).getErrorMsg());
            else setErrorResponse(response,e.getMessage());
        }
    }

    private boolean isAllowed(String requestURI) {
        return Arrays.stream(allowedPath).anyMatch(e->e.contains(requestURI));
    }


    private void setErrorResponse(HttpServletResponse response, ErrorMsg errorMsg) throws IOException {
      PrintWriter out = response.getWriter();
      out.write(setResponse(errorMsg,null,null));
      out.flush();
      return;
    }
    private void setErrorResponse(HttpServletResponse response, String message) throws IOException {

        PrintWriter out = response.getWriter();
        ErrorMsg errorMsg = ErrorMsg.FAILED;
        out.write(setResponse(null,400,message));
        out.flush();
        return;
    }

    private String extractToken(String header) throws AuthException {
        try {
            return header.substring(7);
        }catch (Exception e){
            throw new AuthException(ErrorMsg.TOKEN_EMPTY);
        }
    }

    private String setResponse(ErrorMsg errorMsg, Integer code, String message) throws JsonProcessingException {
        Map<String,Object> errorResonse = new HashMap<>();
        if(!Objects.isNull(errorMsg)) {
            errorResonse.put("code", errorMsg.getCode());
            errorResonse.put("message", errorMsg.getMessage());
        }else {
            errorResonse.put("code", code);
            errorResonse.put("message",message);
        }
        errorResonse.put("dateTime", new SimpleDateFormat("dd MMMM YYYY HH:mm:ss").format(new Date()));

        return objectMapper.writeValueAsString(errorResonse);
    }




}
