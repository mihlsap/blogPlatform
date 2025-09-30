package com.example.blogplatform.controllers;

import com.example.blogplatform.domain.dtos.CreateTagRequest;
import com.example.blogplatform.domain.dtos.TagResponse;
import com.example.blogplatform.services.TagService;
import com.example.blogplatform.utils.GenericResponse;
import com.example.blogplatform.utils.ResponseMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<GenericResponse<List<TagResponse>>> getAllTags() {
        List<TagResponse> tags = tagService.getAllTags();
        if (!tags.isEmpty()) {
            return GenericResponse.success(ResponseMessage.TAGS_FOUND.message, ResponseMessage.TAGS_FOUND.status, tags);
        } else {
            return GenericResponse.success(ResponseMessage.NO_TAGS_FOUND.message,  ResponseMessage.NO_TAGS_FOUND.status, tags);
        }
    }

    @PostMapping
    public ResponseEntity<GenericResponse<TagResponse>> createTag(
            @Valid @RequestBody CreateTagRequest createRequest
    ) {
        return GenericResponse.success(
                ResponseMessage.CATEGORY_ADDED.message,
                ResponseMessage.CATEGORY_ADDED.status,
                tagService.createTag(createRequest)
        );
    }

    @DeleteMapping("/{tag_id}")
    public ResponseEntity<GenericResponse<TagResponse>> deleteTag(@Valid @PathVariable("tag_id") UUID id) {
        return GenericResponse.success(
                ResponseMessage.CATEGORY_REMOVED.message,
                ResponseMessage.CATEGORY_REMOVED.status,
                tagService.deleteTag(id)
        );
    }
}
