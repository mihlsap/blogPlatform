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

/**
 * REST controller for managing blog post categories.
 * <p>
 * Provides endpoints for listing, creating, and deleting categories.
 * All responses are wrapped in a {@link GenericResponse} to maintain
 * consistent response structure.
 */
@RestController
@RequestMapping(path = "/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Retrieves a list of all blog post categories.
     *
     * <p>Returns a {@link ResponseEntity} wrapping a {@link GenericResponse} containing
     * a list of {@link CategoryDto} and an appropriate HTTP status and message.</p>
     *
     * <ul>
     *     <li>{@code 200 OK} with the list of categories if any exist.</li>
     *     <li>{@code 200 OK} with an empty list and a message indicating no categories found if none exist.</li>
     * </ul>
     *
     * @return a {@link ResponseEntity} containing the categories wrapped in a {@link GenericResponse}
     */
    @GetMapping
    public ResponseEntity<GenericResponse<List<CategoryDto>>> listCategories() {
        List<CategoryDto> categories = categoryService.listCategories();
        if (!categories.isEmpty()) {
            return GenericResponse.success(ResponseMessage.CATEGORIES_FOUND.message, ResponseMessage.CATEGORIES_FOUND.status, categories);
        } else {
            return GenericResponse.success(ResponseMessage.NO_CATEGORIES_FOUND.message, ResponseMessage.NO_CATEGORIES_FOUND.status, categories);
        }
    }

    /**
     * Creates a new blog post category.
     *
     * @param categoryRequest the request body containing category name and validation constraints
     * @return a {@link ResponseEntity} containing a {@link GenericResponse} with the created {@link CategoryDto}
     *         and a success message
     *
     * @throws IllegalArgumentException if a category with the given name already exists
     */
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

    /**
     * Deletes an existing category by its ID.
     * <p>
     * The category must not contain any posts. If it does, the request is rejected.
     *
     * @param id the UUID of the category to delete
     * @return a {@link ResponseEntity} containing a {@link GenericResponse} with {@code null} data
     *         and a success message
     *
     * @throws IllegalArgumentException if the category with the given ID does not exist
     * @throws IllegalStateException if the category contains posts and cannot be deleted
     */
    @DeleteMapping("/{category_id}")
    public ResponseEntity<GenericResponse<CategoryDto>> deleteCategory(@Valid @PathVariable("category_id") UUID id) {
        return GenericResponse.success(
                ResponseMessage.CATEGORY_REMOVED.message,
                ResponseMessage.CATEGORY_REMOVED.status,
                categoryService.deleteCategory(id)
        );
    }
}
