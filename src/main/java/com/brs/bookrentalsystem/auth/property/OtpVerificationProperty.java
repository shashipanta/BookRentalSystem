package com.brs.bookrentalsystem.auth.property;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpVerificationProperty {

    private String userName;
    private String otp;
    private String email;
}
