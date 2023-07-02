package com.brs.bookrentalsystem.auth.service;


public interface TokenService {

    void storeToken(String token, String userMail);

    void deleteToken(Short tokenId);
}
