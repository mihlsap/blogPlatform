package com.example.blogplatform.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Data Transfer Object (DTO) representing a category in the blog platform.
 *
 * <p>This DTO is typically used to expose category data to API consumers
 * without exposing the full entity model.
 *
 * <p>Includes basic category information such as:
 * <ul>
 *     <li>{@code id} – Unique identifier of the category</li>
 *     <li>{@code name} – Name of the category</li>
 *     <li>{@code postCount} – Number of posts associated with the category</li>
 * </ul>
 *
 * <p>Lombok annotations reduce boilerplate:
 * <ul>
 *     <li>{@code @Data} – Generates getters, setters, toString, equals, and hashCode</li>
 *     <li>{@code @Builder} – Enables the builder pattern</li>
 *     <li>{@code @NoArgsConstructor}, {@code @AllArgsConstructor} – Constructors</li>
 * </ul>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    /**
     * The unique identifier of the category.
     */
    private UUID id;

    /**
     * The name of the category.
     */
    private String name;

    /**
     * The number of posts associated with the category.
     */
    private long postCount;
}
