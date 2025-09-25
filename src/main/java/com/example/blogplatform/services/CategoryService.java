package com.example.blogplatform.services;

import com.example.blogplatform.domain.dtos.CategoryDto;
import com.example.blogplatform.domain.dtos.CreateCategoryRequest;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<CategoryDto> listCategories();

    CategoryDto createCategory(CreateCategoryRequest categoryRequest);

    CategoryDto deleteCategory(UUID id);
}
