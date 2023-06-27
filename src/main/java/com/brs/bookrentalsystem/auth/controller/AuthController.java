package com.brs.bookrentalsystem.auth.controller;

import com.brs.bookrentalsystem.auth.dto.LoginRequest;
import com.brs.bookrentalsystem.auth.dto.RegistrationRequest;
import com.brs.bookrentalsystem.auth.service.UserAccountService;
import com.brs.bookrentalsystem.dto.Message;
import com.brs.bookrentalsystem.error.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/login")
    public String openLoginPage(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "auth/login";
    }


    @GetMapping("/register")
    public String openRegistrationPage(Model model, HttpServletRequest httpServletRequest) {

        System.out.println(httpServletRequest.getRemoteAddr());
        if(!model.containsAttribute("registrationRequest")){
            model.addAttribute("registrationRequest", new RegistrationRequest());

        }
        return "auth/register";
    }

    @PostMapping("/register/submit")
    public String registerNewUser(
            @ModelAttribute(name = "registrationRequest") RegistrationRequest request,
            HttpServletRequest httpServletRequest,
            RedirectAttributes ra) {
        request.setIp(httpServletRequest.getRemoteAddr());
        Message message = userAccountService.registerNewUser(request);

        ra.addFlashAttribute("errorResponse", new ErrorResponse(message.getCode(), message.getMessage()));
        return "redirect:/brs/auth/login";
    }
}
