package com.billmanager.restcall.dto;

public record LoginResponse(
        String name,
        long mobileNo,
        String accessToken,
        String refreshToken
) {
}
