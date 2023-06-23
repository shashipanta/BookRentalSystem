package com.brs.bookrentalsystem.auth.repo;

import com.brs.bookrentalsystem.auth.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepo extends JpaRepository<UserAccount, Integer> {

}
