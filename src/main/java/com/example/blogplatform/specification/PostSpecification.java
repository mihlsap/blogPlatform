package com.example.blogplatform.specification;

import com.example.blogplatform.domain.PostStatus;
import com.example.blogplatform.domain.entities.Post;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class PostSpecification {

    private PostSpecification() {}

    public static Specification<Post> hasStatus(PostStatus status) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Post> hasCategory(UUID categoryId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (categoryId == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(root.get("category").get("id"), categoryId);
        };
    }

    public static Specification<Post> hasAuthor(UUID authorId) {
        return (root, query, criteriaBuilder) -> {
            if (authorId == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(root.get("author").get("id"), authorId);
        };
    }

    public static Specification<Post> hasTag(UUID tagId) {
        return (root, query, criteriaBuilder) -> {
            if (tagId == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(root.join("tags").get("id"), tagId);
        };
    }
}
