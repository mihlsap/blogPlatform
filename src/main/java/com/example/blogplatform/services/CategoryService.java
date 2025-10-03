package com.example.blogplatform.services;

import com.example.blogplatform.domain.dtos.CategoryDto;
import com.example.blogplatform.domain.dtos.CreateCategoryRequest;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing blog categories.
 * <p>
 * Provides methods for retrieving, creating, and deleting categories.
 */
public interface CategoryService {

    /**
     * Retrieves a list of all categories.
     * Each category includes a count of associated published posts.
     *
     * @return list of {@link CategoryDto}
     */
    List<CategoryDto> listCategories();

    /**
     * Creates a new category based on the provided request.
     *
     * @param categoryRequest the request containing category details
     * @return the created {@link CategoryDto}
     * @throws IllegalArgumentException if a category with the same name already exists
     */
    CategoryDto createCategory(CreateCategoryRequest categoryRequest);

    /**
     * Deletes the category with the given ID.
     * <p>
     * A category cannot be deleted if it has posts associated with it.
     *
     * @param id the UUID of the category to delete
     * @return {@code null} or the deleted {@link CategoryDto} (implementation-dependent)
     * @throws IllegalArgumentException if the category does not exist
     * @throws IllegalStateException    if the category has associated posts
     */
    CategoryDto deleteCategory(UUID id);
}
