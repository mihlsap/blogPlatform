package com.example.blogplatform.services;

import com.example.blogplatform.domain.dtos.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> listCategories();
}
