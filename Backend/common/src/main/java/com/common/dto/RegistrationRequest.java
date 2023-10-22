package com.common.dto;


import com.common.util.ValidateMsg;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegistrationRequest(
        @NotBlank
        @Size(min = 5,max=20)
        String name,
        @NotBlank
        @Size(min = 10,max = 10)
        Long mobileNo,
        @NotBlank
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\\\S+$).{8,20}$",message = ValidateMsg.PASSWORD_MSG)
        String password,
        @NotBlank
        String fingerId
) {
}
