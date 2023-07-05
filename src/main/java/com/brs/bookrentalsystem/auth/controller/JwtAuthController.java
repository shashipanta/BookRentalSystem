package com.brs.bookrentalsystem.auth.controller;


import com.brs.bookrentalsystem.auth.dto.AuthRequest;
import com.brs.bookrentalsystem.auth.dto.AuthResponse;
import com.brs.bookrentalsystem.auth.service.jwt.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "brs/auth")
public class JwtAuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> attemptLogin(@RequestBody AuthRequest authRequest){
        return new ResponseEntity<>(authService.generateToken(authRequest), HttpStatus.OK);
    }
}
