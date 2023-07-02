package com.brs.bookrentalsystem.controller.thymeleaf.category;

import com.brs.bookrentalsystem.dto.Message;
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

    @GetMapping(value = {"/", ""})
    public String openCategoryPage(Model model) {

        if(model.getAttribute("categoryRequest") == null){
            model.addAttribute("categoryRequest", new CategoryRequest());
            model.addAttribute("showModal", false);
        } else {
            // display category edit form
            model.addAttribute("showModal", true);
        }
        // else edit response

        List<CategoryResponse> allCategories = categoryService.getAllCategories();

        model.addAttribute("categoriesList", allCategories);

        return "/category/category-page";
    }

    @GetMapping(value = "/save")
    public String saveNewCategory(
            @Valid @ModelAttribute("categoryRequest") CategoryRequest request,
            BindingResult bindingResult,
            RedirectAttributes ra,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            System.out.println("Binding errors" + bindingResult.getFieldErrors());
            model.addAttribute("categoryRequest", model.getAttribute("categoryRequest"));
            List<CategoryResponse> allCategories = categoryService.getAllCategories();
            model.addAttribute("categoriesList", allCategories);
            // for form popup
            model.addAttribute("showForm", true);
            return "/category/category-page";
        }

        CategoryResponse category = categoryService.createCategory(request);

        if(request.getId() == null){
            ra.addFlashAttribute("message", new Message("CREATED", "Category created successfully"));
        } else {
            ra.addFlashAttribute("message", new Message("UPDATED", "Category updated successfully"));
        }


        return "redirect:/brs/admin/category/";
    }

    // http://localhost:8080/brs/admin/category/{id}/edit
    @GetMapping(value = "/{id}/edit")
    public String editCategory(
            @PathVariable("id") Short categoryId,
            RedirectAttributes ra
    ) {

        Category categoryById = categoryService.getCategoryById(categoryId);

        ra.addFlashAttribute("categoryRequest", categoryById);

        return "redirect:/brs/admin/category/";
    }

    @GetMapping(value = "/{id}/delete")
    public String deleteCategory(
            @PathVariable("id") Short categoryId,
            RedirectAttributes ra
    ) {

        Message message = categoryService.deleteCategoryById(categoryId);

        ra.addFlashAttribute("message", message);

        return "redirect:/brs/admin/category/";
    }
}
