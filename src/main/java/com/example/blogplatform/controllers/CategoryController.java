package com.example.blogplatform.controllers;

import com.example.blogplatform.domain.dtos.CategoryDto;
import com.example.blogplatform.services.CategoryService;
import com.example.blogplatform.utils.GenericResponse;
import com.example.blogplatform.utils.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<GenericResponse<List<CategoryDto>>> listCategories() {
        List<CategoryDto> categories = categoryService.listCategories();
        if (categories != null && !categories.isEmpty()) {
            return GenericResponse.success(ResponseMessage.CATEGORIES_FOUND.message, ResponseMessage.CATEGORIES_FOUND.status, categories);
        } else {
            return GenericResponse.error(ResponseMessage.CATEGORIES_NOT_FOUND.message, ResponseMessage.CATEGORIES_NOT_FOUND.status);
        }
    }
}
