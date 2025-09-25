package com.example.blogplatform.domain.dtos;

import com.example.blogplatform.domain.entities.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO used when creating a new {@link Category}.
 * <p>
 * Contains validation constraints to ensure the category name is not blank,
 * has a valid length, and includes only allowed characters (letters, digits, spaces, underscores, hyphens).
 * <p>
 * Used in {@code POST /api/v1/categories} endpoint and passed to service layer.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryRequest {

    /**
     * Name of the category to be created.
     * <p>
     * Must be 2–50 characters long and match the pattern: letters, numbers, spaces, underscores, hyphens.
     */
    @NotBlank(message = "Category name is required.")
    @Size(min = 2, max = 50, message = "Category's name length must be between {min} and {max} characters")
    @Pattern(regexp = "^[\\w\\s-]+$", message = "Category name can only contain letters, numbers, spaces, underscores and hyphens")
    private String name;
}
