package com.brs.bookrentalsystem.auth.service.impl;

import com.brs.bookrentalsystem.auth.dto.RegistrationRequest;
import com.brs.bookrentalsystem.auth.dto.userAccount.UserAccountRequest;
import com.brs.bookrentalsystem.auth.enums.RoleNames;
import com.brs.bookrentalsystem.auth.model.Role;
import com.brs.bookrentalsystem.auth.model.UserAccount;
import com.brs.bookrentalsystem.auth.repo.RoleRepo;
import com.brs.bookrentalsystem.auth.repo.UserAccountRepo;
import com.brs.bookrentalsystem.auth.service.UserAccountService;
import com.brs.bookrentalsystem.dto.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepo userAccountRepo;
    private final RoleRepo roleRepo;

    @Override
    public Message registerNewUser(RegistrationRequest request) {

        UserAccount userAccount = toUserAccount(request);

        // Assign default role to newly registered user

        userAccount.setRoles(Set.of(roleRepo.findById(Short.valueOf("1")).get()));

        userAccountRepo.save(userAccount);


        return new Message("CREATED", "User Account created successfully");
    }


    private UserAccount toUserAccount(UserAccountRequest request){
        UserAccount userAccount = new UserAccount();
        userAccount.setId(request.getId());
        userAccount.setUserName(request.getUserName());
        userAccount.setEmail(request.getEmail());
        //TODO :: Bcrypt password encryption
        userAccount.setPassword(request.getPassword());
        userAccount.getIp();

        return userAccount;
    }

    private UserAccount toUserAccount(RegistrationRequest request){
        UserAccount userAccount = new UserAccount();

        userAccount.setUserName(request.getUserName());
        userAccount.setEmail(request.getEmail());
        userAccount.setPassword(request.getPassword());
        userAccount.setIp(request.getIp());

        return userAccount;
    }

    private String extractUserInformation(){
        return null;
    }
}
