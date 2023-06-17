package com.brs.bookrentalsystem.controller.thymeleaf;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/brs")
public class HomeController {

    //http://localhost:8080/brs/dashboard

    @GetMapping(value = "/")
    public String getDashboard(Model model){
        return "index";
    }
}
