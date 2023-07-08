package com.brs.bookrentalsystem.auth.controller;

import com.brs.bookrentalsystem.auth.dto.forgotPassword.ChangePasswordRequest;
import com.brs.bookrentalsystem.auth.dto.forgotPassword.ForgotPasswordRequest;
import com.brs.bookrentalsystem.auth.dto.LoginRequest;
import com.brs.bookrentalsystem.auth.dto.RegistrationRequest;
import com.brs.bookrentalsystem.auth.dto.forgotPassword.VerifyOtpRequest;
import com.brs.bookrentalsystem.auth.service.UserAccountService;
import com.brs.bookrentalsystem.auth.service.impl.ForgotPasswordService;
import com.brs.bookrentalsystem.dto.Message;
import com.brs.bookrentalsystem.error.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/brs/auth")
public class AuthController {

    private static final String UI_MESSAGE = "message";
    private static final String REGISTRATION_REQUEST = "registrationRequest";

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserAccountService userAccountService;

    private final ForgotPasswordService forgotPasswordService;

    @GetMapping("/login")
    public String openLoginPage(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "auth/login";
    }


    @GetMapping("/register")
    public String openRegistrationPage(Model model, HttpServletRequest httpServletRequest) {

        System.out.println(httpServletRequest.getRemoteAddr());
        if (!model.containsAttribute(REGISTRATION_REQUEST)) {
            model.addAttribute(REGISTRATION_REQUEST, new RegistrationRequest());

        }
        return "auth/register";
    }

    @PostMapping("/register/submit")
    public String registerNewUser(
            @Valid @ModelAttribute(name = REGISTRATION_REQUEST) RegistrationRequest request,

            BindingResult bindingResult,
            RedirectAttributes ra,
            Model model) {

        if(bindingResult.hasErrors()){
            logger.error("Binding Error : {}", bindingResult.getFieldError());

            model.addAttribute(REGISTRATION_REQUEST, request);
            return "auth/register";
        }
//        request.setIp(httpServletRequest.getRemoteAddr());
        Message message = userAccountService.registerNewUser(request);

        ra.addFlashAttribute("errorResponse", new ErrorResponse(message.getCode(), message.getMessage()));
        return "redirect:/brs/auth/login";
    }

    @GetMapping(value = "/forgot-password")
    public String openForgotPasswordPage(Model model) {

        model.addAttribute("forgotPasswordRequest", new ForgotPasswordRequest());

        return "auth/forgot-password";
    }


    @PostMapping(value = "/send-otp")
    public String sendOtp(
            @Valid @ModelAttribute("forgotPasswordRequest") ForgotPasswordRequest request,
            BindingResult bindingResult,
            Model model) {
        boolean isUserVerified = forgotPasswordService.verifyEmailAndSendOtp(request.getEmail());

        if (bindingResult.hasErrors()) {
            logger.error("Binding Error : {}", bindingResult.getFieldError());

            model.addAttribute("forgotPasswordRequest", new ForgotPasswordRequest());
            return "/auth/forgot-password";
        }
        Message message = new Message();
        if (isUserVerified) {
            // mail send
            forgotPasswordService.sendOtpToEmail(request.getEmail());
            message.setCode("OTP");
            message.setMessage("OTP sent! Please verify");
            model.addAttribute(UI_MESSAGE, message);
            model.addAttribute("verifyOtpRequest", new VerifyOtpRequest(request.getEmail(), null));
            return "auth/verify-otp";
        } else {
            message.setCode("FAILED");
            message.setMessage("User not found");
        }

        model.addAttribute(UI_MESSAGE, message);

        return "auth/forgot-password";

    }

    @PostMapping(value = "/verify-otp")
    public String verifyOtp(
            @Valid @ModelAttribute("verifyOtpRequest") VerifyOtpRequest request,
                            BindingResult bindingResult,
                            Model model){

        if(bindingResult.hasErrors()){
            logger.error("Binding Error : {}", bindingResult.getFieldError());
            return "auth/verify-otp";
        }
        boolean isOtpVerified = forgotPasswordService.verifyOtp(request);


        if(isOtpVerified){
            model.addAttribute("isOtpVerified", true);
            model.addAttribute("changePasswordRequest", new ChangePasswordRequest(request.getEmail(), null));
            logger.info("VERIFIED : {} otp has been verified", request.getEmail());
            return "auth/change-password";
        }

        model.addAttribute(UI_MESSAGE, new Message("FAILED", "OTP is not matching"));

        return "auth/verify-otp";
    }

    // change password

    @RequestMapping(value = "/change-password")
    public String changePassword(
            @Valid @ModelAttribute("changePasswordRequest") ChangePasswordRequest request,
                            BindingResult bindingResult,
                            Model model){

        if(bindingResult.hasErrors()){
            logger.error("Binding Error : {}", bindingResult.getFieldError());
            return "auth/change-password";
        }
        boolean isPasswordChanged = forgotPasswordService.changePassword(request);
        if(isPasswordChanged){
            model.addAttribute(UI_MESSAGE, new Message("SUCCESS", "Password changed successfully"));
            logger.info("SUCCESS : Password changed for {}", request.getEmail());
        }

        return "auth/login";
    }

}
