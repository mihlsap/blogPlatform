package com.example.blogplatform.controllers;

import com.example.blogplatform.domain.dtos.CategoryDto;
import com.example.blogplatform.domain.dtos.CreateCategoryRequest;
import com.example.blogplatform.services.CategoryService;
import com.example.blogplatform.utils.GenericResponse;
import com.example.blogplatform.utils.ResponseMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
        } else if (categories != null) {
            return GenericResponse.success(ResponseMessage.CATEGORIES_NOT_FOUND.message, ResponseMessage.CATEGORIES_NOT_FOUND.status, categories);
        } else {
            return GenericResponse.error(ResponseMessage.INVALID_INPUT.message,  ResponseMessage.INVALID_INPUT.status);
        }
    }

    @PostMapping
    public ResponseEntity<GenericResponse<CategoryDto>> createCategory(
            @Valid @RequestBody CreateCategoryRequest categoryRequest
    ) {
        return GenericResponse.success(
                ResponseMessage.CATEGORY_ADDED.message,
                ResponseMessage.CATEGORY_ADDED.status,
                categoryService.createCategory(categoryRequest)
        );
    }

    @DeleteMapping("/{category_id}")
    public ResponseEntity<GenericResponse<CategoryDto>> deleteCategory(@Valid @PathVariable("category_id") UUID id) {
        return GenericResponse.success(
                ResponseMessage.CATEGORY_REMOVED.message,
                ResponseMessage.CATEGORY_REMOVED.status,
                categoryService.deleteCategory(id)
        );
    }
}
