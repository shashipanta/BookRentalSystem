package com.brs.bookrentalsystem.auth.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class LoginRequest {

    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Password should be provided")
    private String password;

}
