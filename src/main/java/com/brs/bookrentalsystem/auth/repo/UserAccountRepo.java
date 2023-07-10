package com.brs.bookrentalsystem.auth.repo;

import com.brs.bookrentalsystem.auth.model.UserAccount;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserAccountRepo extends JpaRepository<UserAccount, Integer> {

    Optional<UserAccount> findUserAccountByEmail(String email);

}
