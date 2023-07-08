package com.brs.bookrentalsystem.auth.repo;

import com.brs.bookrentalsystem.auth.model.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepo extends JpaRepository<UserToken, Short> {

    UserToken findUserTokenByEmail(String email);

    Optional<UserToken> findUserTokenByEmailAndAndOtp(String email, String otp);

}
