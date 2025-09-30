package com.example.blogplatform.services;

import com.example.blogplatform.domain.dtos.CreateTagRequest;
import com.example.blogplatform.domain.dtos.TagResponse;

import java.util.List;
import java.util.UUID;

public interface TagService {

    List<TagResponse> getAllTags();

    TagResponse createTag(CreateTagRequest createTagRequest);

    TagResponse deleteTag(UUID id);
}
