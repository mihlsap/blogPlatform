package com.example.blogplatform.controllers;

import com.example.blogplatform.domain.dtos.CreateTagRequest;
import com.example.blogplatform.domain.dtos.TagDto;
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
    public ResponseEntity<GenericResponse<List<TagDto>>> getAllTags() {
        List<TagDto> tags = tagService.getAllTags();
        if (!tags.isEmpty()) {
            return GenericResponse.success(ResponseMessage.TAGS_FOUND.message, ResponseMessage.TAGS_FOUND.status, tags);
        } else {
            return GenericResponse.success(ResponseMessage.NO_TAGS_FOUND.message,  ResponseMessage.NO_TAGS_FOUND.status, tags);
        }
    }

    @PostMapping
    public ResponseEntity<GenericResponse<TagDto>> createTag(
            @Valid @RequestBody CreateTagRequest createRequest
    ) {
        return GenericResponse.success(
                ResponseMessage.TAG_ADDED.message,
                ResponseMessage.TAG_ADDED.status,
                tagService.createTag(createRequest)
        );
    }

    @DeleteMapping("/{tag_id}")
    public ResponseEntity<GenericResponse<TagDto>> deleteTag(@Valid @PathVariable("tag_id") UUID id) {
        return GenericResponse.success(
                ResponseMessage.TAG_REMOVED.message,
                ResponseMessage.TAG_REMOVED.status,
                tagService.deleteTag(id)
        );
    }
}
