package com.brs.bookrentalsystem.auth.service.impl;

import com.brs.bookrentalsystem.auth.model.CustomUserDetails;
import com.brs.bookrentalsystem.auth.model.UserAccount;
import com.brs.bookrentalsystem.auth.repo.UserAccountRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MyCustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserAccountRepo userAccountRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAccount> userAccountByEmail = userAccountRepo.findUserAccountByEmail(username);
        return userAccountByEmail.map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user email does not exist"));
    }
}
