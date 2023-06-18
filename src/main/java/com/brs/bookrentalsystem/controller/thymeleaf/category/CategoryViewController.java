package com.brs.bookrentalsystem.controller.thymeleaf.category;

import com.brs.bookrentalsystem.dto.category.CategoryRequest;
import com.brs.bookrentalsystem.dto.category.CategoryResponse;
import com.brs.bookrentalsystem.model.Category;
import com.brs.bookrentalsystem.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "brs/admin/category")
public class CategoryViewController {

    private final CategoryService categoryService;

    @GetMapping(value = "/")
    public String openCategoryPage(Model model) {

        if(!model.containsAttribute("categoryRequest")){
            model.addAttribute("categoryRequest", new CategoryRequest());
        } else {
            model.addAttribute("showModal", true);
        }
        // else edit response

        List<CategoryResponse> allCategories = categoryService.getAllCategories();

        model.addAttribute("categoriesList", allCategories);

        return "/category/category-page";
    }

    @RequestMapping(value = "/save")
    public String saveNewCategory(
            @Valid @ModelAttribute("categoryRequest") CategoryRequest request,
            BindingResult bindingResult,
            RedirectAttributes ra
    ) {
        if (bindingResult.hasErrors()) {
            System.out.println("Binding errors" + bindingResult.getFieldErrors());
            return "redirect:/brs/admin/category/";
        }

        CategoryResponse category = categoryService.createCategory(request);

        ra.addFlashAttribute("message", category);

        return "redirect:/brs/admin/category/";
    }

    // http://localhost:8080/brs/admin/category/{id}/edit
    @RequestMapping(value = "/{id}/edit")
    public String editCategory(
            @PathVariable("id") Short categoryId,
            RedirectAttributes ra
    ) {

        Category categoryById = categoryService.getCategoryById(categoryId);

        ra.addFlashAttribute("categoryRequest", categoryById);

        return "redirect:/brs/admin/category/";
    }
}
