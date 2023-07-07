package com.brs.bookrentalsystem.auth.service.impl;

import com.brs.bookrentalsystem.auth.dto.ForgotPasswordRequest;
import com.brs.bookrentalsystem.auth.model.UserAccount;
import com.brs.bookrentalsystem.auth.repo.TokenRepo;
import com.brs.bookrentalsystem.auth.repo.UserAccountRepo;
import com.brs.bookrentalsystem.auth.service.TokenService;
import com.brs.bookrentalsystem.util.RandomAlphaNumericString;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ForgotPasswordService {

    private final UserAccountRepo userAccountRepo;
    private final TokenService tokenService;
    private final TokenRepo tokenRepo;

    private final PasswordEncoder passwordEncoder;

    private final RandomAlphaNumericString randomAlphaNumericString;
    private final JavaMailSender javaMailSender;


    public boolean verifyEmailAndSendOtp(String userEmail){

        Optional<UserAccount> userAccountByEmail = userAccountRepo.findUserAccountByEmail(userEmail);
        return userAccountByEmail.isPresent();
    }

    @Async
    public void sendOtpToEmail(String to){
        String generatedOtp = randomAlphaNumericString.generateRandomString(5);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("shashipanta57@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setText("Your OTP is : " + "   "+ generatedOtp);
        simpleMailMessage.setSubject("RESET PASSWORD");


        javaMailSender.send(simpleMailMessage);

        // save otp and email in table
        tokenService.storeToken(generatedOtp, to);

    }

    public boolean verifyOtp(String otp, ForgotPasswordRequest request){
        // otp verification
        if(otp.equals(request.getOtp())){
            // verified and delete token
            tokenService.deleteToken(tokenRepo.findUserTokenByEmailAndAndOtp(request.getEmail(),otp ).getId());
            return true;
        }
        return false;
    }

    public boolean changePassword(ForgotPasswordRequest request){
        Optional<UserAccount> userAccountByEmail = userAccountRepo.findUserAccountByEmail(request.getEmail());

        if (userAccountByEmail.isPresent()) {
            UserAccount userAccount = userAccountByEmail.get();
            userAccount.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userAccountRepo.save(userAccount);
            return true;
        }

        return false;
    }

}
