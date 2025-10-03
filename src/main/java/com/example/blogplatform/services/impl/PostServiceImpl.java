package com.example.blogplatform.services.impl;

import com.example.blogplatform.domain.PostStatus;
import com.example.blogplatform.domain.dtos.CreatePostRequest;
import com.example.blogplatform.domain.dtos.PostDto;
import com.example.blogplatform.domain.entities.Post;
import com.example.blogplatform.mappers.PostMapper;
import com.example.blogplatform.repositories.CategoryRepository;
import com.example.blogplatform.repositories.PostRepository;
import com.example.blogplatform.repositories.TagRepository;
import com.example.blogplatform.repositories.UserRepository;
import com.example.blogplatform.services.PostService;
import com.example.blogplatform.specification.PostSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private static final Integer WORDS_PER_MINUTE = 200;

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    @Override
    public List<PostDto> getAllPostsWithCriteria(UUID categoryId, UUID userId, UUID tagId) {
        return postRepository.findAll(
                        PostSpecification.hasStatus(PostStatus.PUBLISHED)
                                .and(PostSpecification.hasCategory(categoryId))
                                .and(PostSpecification.hasAuthor(userId))
                                .and(PostSpecification.hasTag(tagId)))
                .stream()
                .map(postMapper::toDto)
                .toList();
    }

    @Override
    public List<PostDto> getUserDrafts(UUID userId) {
        return postRepository.findAll(
                PostSpecification.hasAuthor(userId)
                        .and(PostSpecification.hasStatus(PostStatus.DRAFT)))
                .stream()
                .map(postMapper::toDto)
                .toList();
    }

    @Override
    public PostDto addPost(CreatePostRequest createPostRequest, UUID userId) {
        LocalDateTime now = LocalDateTime.now();
        Post post = new Post();
        post.setTitle(createPostRequest.getTitle());
        post.setContent(createPostRequest.getContent());
        post.setStatus(createPostRequest.getStatus());
        post.setAuthor(userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("User with id: " + userId + " not found!")));
        post.setCreatedAt(now);
        post.setUpdatedAt(now);
        post.setReadingTime(calculateReadingTime(createPostRequest.getContent()));
        post.setCategory(categoryRepository.findById(createPostRequest.getCategoryId()).orElseThrow(() ->
                new IllegalArgumentException("Category with id: " + createPostRequest.getCategoryId() + " not found"))
        );
        post.setTags(createPostRequest.getTagIds()
                .stream()
                .map(tagId -> tagRepository.findById(tagId)
                        .orElseThrow()).collect(Collectors.toSet())
        );
        return postMapper.toDto(postRepository.save(post));
    }




    private Integer calculateReadingTime(String content) {
        if (content == null || content.isEmpty()) {
            return 0;
        }

        int wordCount = content.trim().split("\\s+").length;
        return (int) Math.ceil((double) wordCount / WORDS_PER_MINUTE);
    }
}
