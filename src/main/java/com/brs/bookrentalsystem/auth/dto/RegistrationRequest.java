package com.brs.bookrentalsystem.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    @NotEmpty(message = "UserName should be provided")
    private String userName;

    @Email(message = "Email format is invalid")
    private String email;

    @NotEmpty(message = "Password field cannot be empty")
    private String password;

    private String ip;
}
