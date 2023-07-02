package com.brs.bookrentalsystem.auth.repo;

import com.brs.bookrentalsystem.auth.model.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepo extends JpaRepository<UserToken, Short> {

    UserToken findUserTokenByEmail(String email);

}
