package com.brs.bookrentalsystem.auth.dto.forgotPassword;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgotPasswordRequest {

    @NotBlank(message = "Email field cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

}
