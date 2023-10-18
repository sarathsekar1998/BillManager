package com.billmanager.restcall.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.NonNull;

import static com.billmanager.restcall.util.ValidateMsg.PASSWORD_MSG;

public record RegistrationRequest(
        @NotBlank
        @Size(min = 5,max=20)
        String name,
        @NotBlank
        @Size(min = 10,max = 10)
        Long mobileNo,
        @NotBlank
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\\\S+$).{8,20}$",message =PASSWORD_MSG)
        String password,
        @NotBlank
        String fingerId
) {
}
