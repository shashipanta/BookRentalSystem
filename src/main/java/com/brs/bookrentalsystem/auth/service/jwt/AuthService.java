package com.brs.bookrentalsystem.auth.service.jwt;

import com.brs.bookrentalsystem.auth.dto.AuthRequest;
import com.brs.bookrentalsystem.auth.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponse generateToken(AuthRequest authRequest){
        // check if user is genuine
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        if(authenticate.isAuthenticated()){
            String jwtToken =   jwtService.generateToken(authRequest.getEmail());

            return AuthResponse.builder()
                    .token(jwtToken)
                    .build();
        }else {
            throw new BadCredentialsException("Username or password not matching");
        }

    }
}
