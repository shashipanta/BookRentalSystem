package com.brs.bookrentalsystem.auth.service;

import com.brs.bookrentalsystem.auth.dto.RegistrationRequest;
import com.brs.bookrentalsystem.auth.dto.userAccount.UserAccountRequest;
import com.brs.bookrentalsystem.dto.Message;

public interface UserAccountService {

    Message registerNewUser(RegistrationRequest request);
}
