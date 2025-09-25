package com.example.blogplatform.services.impl;

import com.example.blogplatform.domain.dtos.CategoryDto;
import com.example.blogplatform.domain.dtos.CreateCategoryRequest;
import com.example.blogplatform.mappers.CategoryMapper;
import com.example.blogplatform.repositories.CategoryRepository;
import com.example.blogplatform.services.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> listCategories() {
        return categoryRepository.findAllWithPostCount()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public CategoryDto createCategory(CreateCategoryRequest categoryRequest) {
        if (categoryRepository.existsByNameIgnoreCase(categoryRequest.getName())) {
            throw new IllegalArgumentException("Category already exists with name: " + categoryRequest.getName());
        }
        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(categoryRequest)));
    }

    @Override
    @Transactional
    public CategoryDto deleteCategory(UUID id) {
        if (!categoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
        return null;
    }
}
