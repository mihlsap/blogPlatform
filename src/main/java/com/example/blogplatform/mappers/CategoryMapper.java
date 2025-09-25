package com.example.blogplatform.mappers;

import com.example.blogplatform.domain.PostStatus;
import com.example.blogplatform.domain.dtos.CategoryDto;
import com.example.blogplatform.domain.dtos.CreateCategoryRequest;
import com.example.blogplatform.domain.entities.Category;
import com.example.blogplatform.domain.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper interface for converting between {@link Category} entities and their corresponding
 * DTO/request representations.
 * <p>
 * Uses <a href="https://mapstruct.org/">MapStruct</a> for compile-time code generation to ensure
 * type-safe, performant mappings.
 * </p>
 *
 * <p>
 * This mapper:
 * <ul>
 *     <li>Converts {@link CreateCategoryRequest} to {@link Category}</li>
 *     <li>Converts {@link Category} to {@link CategoryDto}, including a custom calculation of post count</li>
 * </ul>
 * </p>
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    /**
     * Maps a {@link CreateCategoryRequest} DTO to a {@link Category} entity.
     *
     * @param createCategoryRequest the request DTO containing data to create a new category
     * @return a new {@link Category} entity populated from the request
     */
    Category toEntity(CreateCategoryRequest createCategoryRequest);

    /**
     * Maps a {@link Category} entity to a {@link CategoryDto}, including a custom mapping for post count.
     * Only posts with {@link PostStatus#PUBLISHED} are counted.
     *
     * @param category the entity to be mapped
     * @return the corresponding {@link CategoryDto}
     */
    @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
    CategoryDto toDto(Category category);

    /**
     * Calculates the number of published posts in the given category.
     *
     * @param posts the list of posts associated with the category
     * @return the number of posts with {@link PostStatus#PUBLISHED} status; returns {@code 0} if {@code posts} is {@code null}
     */
    @Named("calculatePostCount")
    default long calculatePostCount(List<Post> posts) {
        if (posts == null) {
            return 0;
        }
        return posts.stream()
                .filter(post -> PostStatus.PUBLISHED.equals(post.getStatus()))
                .count();
    }
}
