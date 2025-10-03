package com.example.blogplatform.controllers;

import com.example.blogplatform.domain.dtos.CreatePostRequest;
import com.example.blogplatform.domain.dtos.PostDto;
import com.example.blogplatform.services.PostService;
import com.example.blogplatform.utils.GenericResponse;
import com.example.blogplatform.utils.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping(path = "/{postId}")
    public ResponseEntity<GenericResponse<PostDto>> getPost(@PathVariable UUID postId) {
        return GenericResponse.success(
                ResponseMessage.POST_FOUND.message,
                ResponseMessage.POST_FOUND.status,
                postService.getPost(postId)
        );
    }

    @GetMapping
    public ResponseEntity<GenericResponse<List<PostDto>>> getAllPublishedPostsWithCriteria(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) UUID tagId
    ) {
        List<PostDto> posts = postService.getAllPostsWithCriteria(categoryId, userId, tagId);

        if (!posts.isEmpty()) {
            return GenericResponse.success(
                    ResponseMessage.POSTS_FOUND.message,
                    ResponseMessage.POSTS_FOUND.status,
                    posts
            );
        }

        return GenericResponse.success(
                ResponseMessage.NO_POSTS_FOUND.message,
                ResponseMessage.NO_POSTS_FOUND.status,
                posts
        );
    }

    @GetMapping(path = "/drafts")
    public ResponseEntity<GenericResponse<List<PostDto>>> getUserDrafts(@RequestAttribute(name = "userId") UUID userId) {
        List<PostDto> posts = postService.getUserDrafts(userId);
        if (!posts.isEmpty()) {
            return GenericResponse.success(
                    ResponseMessage.POSTS_FOUND.message,
                    ResponseMessage.POSTS_FOUND.status,
                    posts
            );
        }
        return GenericResponse.success(
                ResponseMessage.NO_POSTS_FOUND.message,
                ResponseMessage.NO_POSTS_FOUND.status,
                posts
        );
    }

    @PostMapping
    public ResponseEntity<GenericResponse<PostDto>> createPost(
            @RequestBody CreatePostRequest createPostRequest,
            @RequestAttribute(name = "userId") UUID userId
    ) {
        return GenericResponse.success(
                ResponseMessage.POST_ADDED.message,
                ResponseMessage.POST_ADDED.status,
                postService.addPost(createPostRequest, userId)
        );
    }

    @PutMapping(path = "/{postId}")
    public ResponseEntity<GenericResponse<PostDto>> updatePost(
            @PathVariable UUID postId,
            @RequestBody CreatePostRequest createPostRequest,
            @RequestAttribute(name = "userId") UUID userId
    ) {
        return GenericResponse.success(
                ResponseMessage.POST_UPDATED.message,
                ResponseMessage.POST_UPDATED.status,
                postService.updatePost(postId, createPostRequest, userId)
        );
    }

    @DeleteMapping(path = "/{postId}")
    public ResponseEntity<GenericResponse<PostDto>> deletePost(
            @PathVariable UUID postId,
            @RequestAttribute UUID userId
            ) {
        return GenericResponse.success(
                ResponseMessage.POST_REMOVED.message,
                ResponseMessage.POST_REMOVED.status,
                postService.removePost(postId, userId)
        );
    }
}
