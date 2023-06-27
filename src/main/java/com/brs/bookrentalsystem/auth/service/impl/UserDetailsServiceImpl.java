//package com.brs.bookrentalsystem.auth.service.impl;
//
//import com.brs.bookrentalsystem.auth.model.UserAccount;
//import com.brs.bookrentalsystem.auth.repo.UserAccountRepo;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Collection;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    private final UserAccountRepo userAccountRepo;
//
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         userAccountRepo.findUserAccountByEmail(username);
//
//        Collection<GrantedAuthority> authorities = userAccount.getRoles().stream()
//                .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
//                .collect(Collectors.toList());
//
//        // User has different implementation : passing email because it is unique
//        User user = new User(userAccount.getEmail(), userAccount.getPassword(), authorities);
//        return user;
//    }
//}
