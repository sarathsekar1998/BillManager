package com.billmanager.restcall.exception;

import com.common.util.ErrorMsg;
import lombok.Data;

@Data
public class AuthException extends RuntimeException {
   private ErrorMsg errorMsg;

    public AuthException(ErrorMsg errorMsg) {
        this.errorMsg = errorMsg;
    }
}
