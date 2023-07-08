package com.brs.bookrentalsystem.auth.dto.forgotPassword;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
public class VerifyOtpRequest {

    // hidden email
    private String email;

    @NotBlank(message = "OTP cannot be blank")
    @Length(min = 5, max = 5, message = "OTP length mismatching")
    private String otp;
}
