package com.example.blogplatform.services.impl;

import com.example.blogplatform.domain.PostStatus;
import com.example.blogplatform.domain.dtos.PostDto;
import com.example.blogplatform.mappers.PostMapper;
import com.example.blogplatform.repositories.PostRepository;
import com.example.blogplatform.services.PostService;
import com.example.blogplatform.specification.PostSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

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
}
