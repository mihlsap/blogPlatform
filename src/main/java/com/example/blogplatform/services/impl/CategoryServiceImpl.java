package com.example.blogplatform.services.impl;

import com.example.blogplatform.domain.dtos.CategoryDto;
import com.example.blogplatform.mappers.CategoryMapper;
import com.example.blogplatform.repositories.CategoryRepository;
import com.example.blogplatform.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> listCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }
}
