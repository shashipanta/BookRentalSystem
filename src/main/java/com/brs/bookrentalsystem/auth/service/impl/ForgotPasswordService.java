package com.brs.bookrentalsystem.auth.service.impl;

import com.brs.bookrentalsystem.auth.dto.forgotPassword.ChangePasswordRequest;
import com.brs.bookrentalsystem.auth.dto.forgotPassword.VerifyOtpRequest;
import com.brs.bookrentalsystem.auth.model.UserAccount;
import com.brs.bookrentalsystem.auth.model.UserToken;
import com.brs.bookrentalsystem.auth.property.OtpVerificationProperty;
import com.brs.bookrentalsystem.auth.repo.TokenRepo;
import com.brs.bookrentalsystem.auth.repo.UserAccountRepo;
import com.brs.bookrentalsystem.auth.service.TokenService;
import com.brs.bookrentalsystem.auth.service.UserAccountService;
import com.brs.bookrentalsystem.util.RandomAlphaNumericString;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ForgotPasswordService {

    private final UserAccountRepo userAccountRepo;
    private final TokenService tokenService;
    private final UserAccountService userAccountService;
    private final TokenRepo tokenRepo;

    private final PasswordEncoder passwordEncoder;

    private final RandomAlphaNumericString randomAlphaNumericString;
    private final JavaMailSender javaMailSender;

    Logger logger = LoggerFactory.getLogger(ForgotPasswordService.class);


    public boolean verifyEmailAndSendOtp(String userEmail) {

        Optional<UserAccount> userAccountByEmail = userAccountRepo.findUserAccountByEmail(userEmail);
        return userAccountByEmail.isPresent();
    }

    @Async
    public void sendOtpToEmail(String to) {
        String generatedOtp = randomAlphaNumericString.generateRandomString(5);

        UserAccount userAccount = userAccountService.findUserAccountByEmail(to);

        OtpVerificationProperty otpProperty = new OtpVerificationProperty(userAccount.getUserName(), generatedOtp, to);

        String emailBody = this.prepareEmailTemplate(otpProperty);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessage.setContent(emailBody, "text/html");
            mimeMessageHelper.setFrom("shashipanta57@gmail.com");
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("PASSWORD RESET");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        javaMailSender.send(mimeMessage);

        // save otp and email in table
        tokenService.storeToken(generatedOtp, to);

    }

    public boolean verifyOtp(VerifyOtpRequest request) {

        // verified and delete token
        Optional<UserToken> userTokenByEmailAndAndOtp =
                tokenRepo.findUserTokenByEmailAndAndOtp(request.getEmail(), request.getOtp());
        if (userTokenByEmailAndAndOtp.isPresent()) {
            tokenService.deleteToken(userTokenByEmailAndAndOtp.get().getId());
            return true;
        } else {
            return false;
        }
    }

    public boolean changePassword(ChangePasswordRequest request) {
        Optional<UserAccount> userAccountByEmail = userAccountRepo.findUserAccountByEmail(request.getEmail());

        if (userAccountByEmail.isPresent()) {
            UserAccount userAccount = userAccountByEmail.get();
            userAccount.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userAccountRepo.save(userAccount);
            return true;
        }

        return false;
    }

    public String prepareEmailTemplate(OtpVerificationProperty otp) {

        File otpHtmlFile;

        String fileContent = null;
        try {
            otpHtmlFile = new ClassPathResource("templates/auth/otp-verification.html").getFile();

            fileContent = FileUtils.readFileToString(otpHtmlFile, "UTF_8");

            Field[] fields = OtpVerificationProperty.class.getDeclaredFields();
            String fieldName;

            for (Field f : fields) {
                fieldName = f.getName();

                switch (fieldName) {
                    case "userName" -> fileContent = fileContent.replace("{{" + fieldName + "}}", otp.getUserName());
                    case "otp" -> fileContent = fileContent.replace("{{" + fieldName + "}}", otp.getOtp());
                    case "email" -> fileContent = fileContent.replace("{{" + fieldName + "}}", otp.getEmail());
                    default -> logger.info("Field not found on template {}", fieldName);
                }
            }
        } catch (IOException e) {
            logger.error("IOException : {}", e.getMessage());
//            throw new RuntimeException(e);
        }

        return fileContent;

    }

}
