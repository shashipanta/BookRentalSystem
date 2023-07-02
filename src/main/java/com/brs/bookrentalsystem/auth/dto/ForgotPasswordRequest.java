package com.brs.bookrentalsystem.auth.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class ForgotPasswordRequest {

    @Email(message = "Invalid email format")
    private String email;

    private String otp;

    private String newPassword;
}
