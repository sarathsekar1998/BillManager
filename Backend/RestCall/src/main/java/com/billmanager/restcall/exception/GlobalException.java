package com.billmanager.restcall.exception;

import com.common.util.ErrorMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class GlobalException {

    @Autowired
    SimpleDateFormat dateFormat;

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Map<String,Object>> authException(AuthException e){
        return new ResponseEntity<Map<String,Object>>(setResponse(e.getErrorMsg(),null,null), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String,Object>> authException(UsernameNotFoundException e){
        return new ResponseEntity<Map<String,Object>>(setResponse(null,1003,e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    private Map<String,Object> setResponse(ErrorMsg errorMsg,Integer code,String message) {
        Map<String,Object> errorResonse = new HashMap<>();
        if(!Objects.isNull(errorMsg)) {
            errorResonse.put("code", errorMsg.getCode());
            errorResonse.put("message", errorMsg.getMessage());
        }else {
            errorResonse.put("code", errorMsg.getCode());
            errorResonse.put("message", errorMsg.getMessage());
        }
        errorResonse.put("dateTime", dateFormat.format(new Date()));
        return errorResonse;
    }


}
