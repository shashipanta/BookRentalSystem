package com.brs.bookrentalsystem.auth.controller;

import com.brs.bookrentalsystem.auth.dto.ForgotPasswordRequest;
import com.brs.bookrentalsystem.auth.dto.LoginRequest;
import com.brs.bookrentalsystem.auth.dto.RegistrationRequest;
import com.brs.bookrentalsystem.auth.service.UserAccountService;
import com.brs.bookrentalsystem.auth.service.impl.ForgotPasswordService;
import com.brs.bookrentalsystem.dto.Message;
import com.brs.bookrentalsystem.error.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.http.HttpRequest;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/brs/auth")
public class AuthController {

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
        if (!model.containsAttribute("registrationRequest")) {
            model.addAttribute("registrationRequest", new RegistrationRequest());

        }
        return "auth/register";
    }

    @PostMapping("/register/submit")
    public String registerNewUser(
            @Valid @ModelAttribute(name = "registrationRequest") RegistrationRequest request,

            BindingResult bindingResult,
            RedirectAttributes ra,
            Model model) {

        if(bindingResult.hasErrors()){
            System.out.println("binding result : " + bindingResult);
            model.addAttribute("registrationRequest", request);
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

//    @GetMapping(value = "/otp-page")
//    public String openVerifyOtpPage(
//            @ModelAttribute("forgotPasswordRequest")ForgotPasswordRequest request,
//            Model model){
//
//
//        return "auth/verify-otp";
//
//    }

    @GetMapping(value = "/send-otp")
    public String sendOtp(
            @Valid @ModelAttribute("forgotPasswordRequest") ForgotPasswordRequest request,
            BindingResult bindingResult,
            Model model) {
        boolean isUserVerified = forgotPasswordService.verifyEmailAndSendOtp(request.getEmail());

        if (bindingResult.hasErrors()) {
            return "/auth/forgot-password";
        }
        Message message = new Message();
        if (isUserVerified) {
            // mail send
            forgotPasswordService.sendOtpToEmail(request.getEmail());
            message.setCode("OTP");
            message.setMessage("OTP sent! Please verify");
//            model.addAttribute("forgotPasswordRequest", request);
            return "auth/verify-otp";
        } else {
            message.setCode("FAILED");
            message.setMessage("User not found");
        }

        return "auth/forgot-password";

    }

    @GetMapping(value = "/verify-otp")
    public String verifyOtp(@ModelAttribute("forgotPasswordRequest") ForgotPasswordRequest request,
                            BindingResult bindingResult,
                            Model model){

        boolean isOtpVerified = forgotPasswordService.verifyOtp(request.getOtp(), request);


        if(isOtpVerified){
            model.addAttribute("isOtpVerified", true);
            model.addAttribute("forgotPasswordRequest", request);
            return "auth/change-password";
        }

        model.addAttribute("message", "Otp not matching");

        return "auth/forgot-password";
    }

    // change password

    @RequestMapping(value = "/change-password")
    public String changePassword(@ModelAttribute("forgotPasswordRequest") ForgotPasswordRequest request,
                            BindingResult bindingResult,
                            Model model){

        boolean isPasswordChanged = forgotPasswordService.changePassword(request);

        return "auth/login";
    }

}
