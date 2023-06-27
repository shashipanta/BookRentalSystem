package com.brs.bookrentalsystem.auth.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    public UserAccount userAccount;

    public String email;
    public String password;
    public List<GrantedAuthority> grantedAuthorityList;


    public CustomUserDetails(UserAccount userAccount){
        this.userAccount = userAccount;
        email = userAccount.getEmail();
        password = userAccount.getPassword();
        grantedAuthorityList = List.of(new SimpleGrantedAuthority(userAccount.getRole().name()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorityList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getUserFullName(){
        return userAccount.getUserName();
    }
}
