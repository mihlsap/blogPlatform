package com.example.blogplatform.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a blog post category in the system.
 *
 * <p>Each {@code Category} has a unique identifier and a unique, non-null name.
 * A category can be associated with multiple blog posts via a one-to-many relationship.
 *
 * <p>This entity is mapped to the {@code categories} table in the database.
 *
 * <p>Lombok annotations are used for boilerplate reduction:
 * <ul>
 *     <li>{@code @Getter}, {@code @Setter} – Generates getters and setters</li>
 *     <li>{@code @Builder} – Enables the builder pattern</li>
 *     <li>{@code @NoArgsConstructor}, {@code @AllArgsConstructor} – Constructors</li>
 * </ul>
 *
 * @see Post
 */
@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Category {

    /**
     * The unique identifier for this category.
     *
     * <p>Generated using {@code UUID} strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The name of the category.
     *
     * <p>This field must be unique and not null.
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * The list of posts associated with this category.
     *
     * <p>This is a one-to-many relationship, where each post is mapped back
     * to this category via the {@code category} field in the {@link Post} entity.
     */
    @OneToMany(mappedBy = "category")
    private List<Post> posts = new ArrayList<>();

    /**
     * Compares this category to another object for equality.
     *
     * <p>Two categories are considered equal if their {@code id} and {@code name} are equal.
     *
     * @param o the object to compare with
     * @return {@code true} if the objects are equal; {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(name, category.name);
    }

    /**
     * Returns the hash code of this category.
     *
     * <p>Based on {@code id} and {@code name}.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
