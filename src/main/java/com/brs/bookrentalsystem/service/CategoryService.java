package com.brs.bookrentalsystem.service;

import com.brs.bookrentalsystem.dto.Message;
import com.brs.bookrentalsystem.dto.category.CategoryRequest;
import com.brs.bookrentalsystem.dto.category.CategoryResponse;
import com.brs.bookrentalsystem.model.Category;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);

    List<CategoryResponse> getAllCategories();

    CategoryResponse findCategoryById(Short categoryId);

    Category getCategoryById(Short categoryId);

    CategoryResponse updateCategory(CategoryRequest request, Short categoryId);

    Message deleteCategoryById(Short categoryId);
}
