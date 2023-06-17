package com.brs.bookrentalsystem.controller.thymeleaf.category;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "brs/admin/category")
public class CategoryViewController {

    @GetMapping(value = "/")
    public String openCategoryPage(Model model){
        return "/category/category-page";
    }
}
