package com.common.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum ErrorMsg {

    USERNAME_EXIST(1000, "Username Already Exist"),
    FAILED(1001, "Something Failed"),
    BADCREDENTIAL(1002,"please check username and password"),
    ALREADY_LOGGED(1003,"Already Logged User..." ),
    ALREADY_LOGGED_OUT(1006,"Already Logged Out..."),
    TOKEN_EMPTY(1004,"token empty"),
    INVALID_TOKEN(1005,"invalid token" );
    private int code;
    private String message;

}
