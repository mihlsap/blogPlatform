package com.example.blogplatform.services;

import com.example.blogplatform.domain.dtos.CreateTagRequest;
import com.example.blogplatform.domain.dtos.TagDto;

import java.util.List;
import java.util.UUID;

public interface TagService {

    List<TagDto> getAllTags();

    TagDto createTag(CreateTagRequest createTagRequest);

    TagDto deleteTag(UUID id);
}
