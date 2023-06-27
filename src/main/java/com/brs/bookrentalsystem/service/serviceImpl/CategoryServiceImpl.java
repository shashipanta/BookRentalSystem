package com.brs.bookrentalsystem.service.serviceImpl;

import com.brs.bookrentalsystem.dto.Message;
import com.brs.bookrentalsystem.dto.category.CategoryRequest;
import com.brs.bookrentalsystem.dto.category.CategoryResponse;
import com.brs.bookrentalsystem.model.Category;
import com.brs.bookrentalsystem.repo.CategoryRepo;
import com.brs.bookrentalsystem.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = toCategory(request);
        category = categoryRepo.save(category);
        return toCategoryResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {

        List<Category> categories = categoryRepo.findAll();
        List<CategoryResponse> categoryResponseList = categories.stream()
                .map(this::toCategoryResponse)
                .collect(Collectors.toList());
        return categoryResponseList;
    }

    @Override
    public CategoryResponse findCategoryById(Short categoryId) {
        return null;
    }


    // handle exception
    @Override
    public Category getCategoryById(Short categoryId) {
        return categoryRepo.findById(categoryId).orElseThrow();
    }

    @Override
    public CategoryResponse updateCategory(CategoryRequest request, Short categoryId) {
        return null;
    }

    @Override
    public Message deleteCategoryById(Short categoryId) {
        categoryRepo.deleteById(categoryId);
        return new Message("DELETED", "Category deleted successfully");
    }

    private CategoryResponse toCategoryResponse(Category category){
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    private Category toCategory(CategoryRequest categoryRequest){
        Category  category = new Category();

        category.setId(categoryRequest.getId());
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());

        return category;
    }


}
