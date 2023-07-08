package com.brs.bookrentalsystem.auth.service.impl;

import com.brs.bookrentalsystem.auth.model.UserToken;
import com.brs.bookrentalsystem.auth.repo.TokenRepo;
import com.brs.bookrentalsystem.auth.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepo tokenRepo;

    @Override
    public void storeToken(String token, String userMail) {
        UserToken userToken = new UserToken();
        userToken.setOtp(token);
        userToken.setEmail(userMail);

        // invalidate any tokens of the same user
        UserToken previousToken = tokenRepo.findUserTokenByEmail(userMail);

        if(previousToken != null) tokenRepo.deleteById(previousToken.getId());

        tokenRepo.save(userToken);
    }

    @Override
    public void deleteToken(Short tokenId) {
        tokenRepo.deleteById(tokenId);
    }
}
