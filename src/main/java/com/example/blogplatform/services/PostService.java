package com.example.blogplatform.services;

import com.example.blogplatform.domain.dtos.CreatePostRequest;
import com.example.blogplatform.domain.dtos.PostDto;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<PostDto> getAllPostsWithCriteria(UUID categoryId, UUID userId, UUID tagId);

    List<PostDto> getUserDrafts(UUID userId);

    PostDto addPost(CreatePostRequest createPostRequest, UUID userId);

    PostDto removePost(UUID postId, UUID userId);

    PostDto getPost(UUID postId);
}
