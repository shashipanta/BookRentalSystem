package com.brs.bookrentalsystem.auth.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {

    private String token;
}
