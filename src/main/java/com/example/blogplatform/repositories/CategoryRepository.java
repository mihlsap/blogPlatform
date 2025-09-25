package com.example.blogplatform.repositories;

import com.example.blogplatform.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for managing {@link Category} entities.
 * <p>
 * Extends Spring Data JPA's {@link JpaRepository}, providing standard CRUD operations.
 * </p>
 *
 * <p>
 * Includes custom methods for:
 * <ul>
 *     <li>Fetching all categories with their associated posts eagerly</li>
 *     <li>Checking existence of a category by name (case-insensitive)</li>
 * </ul>
 * </p>
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    /**
     * Retrieves all categories with their associated posts using a left join fetch.
     * <p>
     * This avoids the N+1 select problem when accessing posts of each category after retrieval.
     * </p>
     *
     * @return a list of {@link Category} entities, each with its {@code posts} initialized
     */
    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.posts")
    List<Category> findAllWithPostCount();

    /**
     * Checks whether a category with the given name (case-insensitive) exists in the database.
     *
     * @param name the name of the category to check
     * @return {@code true} if a category with the given name exists, {@code false} otherwise
     */
    boolean existsByNameIgnoreCase(String name);
}
