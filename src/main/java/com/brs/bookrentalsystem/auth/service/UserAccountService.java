package com.brs.bookrentalsystem.auth.service;

import com.brs.bookrentalsystem.auth.dto.RegistrationRequest;
import com.brs.bookrentalsystem.auth.dto.userAccount.UserAccountRequest;
import com.brs.bookrentalsystem.auth.model.UserAccount;
import com.brs.bookrentalsystem.dto.Message;

import java.util.List;

public interface UserAccountService {

    Message registerNewUser(RegistrationRequest request);

    void registerDefaultUsers(List<UserAccount> defaultUsers);
}
