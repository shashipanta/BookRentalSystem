package com.brs.bookrentalsystem.controller.rest;

import com.brs.bookrentalsystem.dto.category.CategoryRequest;
import com.brs.bookrentalsystem.dto.category.CategoryResponse;
import com.brs.bookrentalsystem.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping(value = "/")
    public CategoryResponse registerNewCategory(@RequestBody @Valid CategoryRequest categoryRequest){
        return categoryService.createCategory(categoryRequest);
    }
}
