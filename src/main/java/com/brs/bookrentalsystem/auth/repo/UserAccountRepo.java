package com.brs.bookrentalsystem.auth.repo;

import com.brs.bookrentalsystem.auth.model.UserAccount;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepo extends JpaRepository<UserAccount, Integer> {

    Optional<UserAccount> findUserAccountByEmail(String email);

}
