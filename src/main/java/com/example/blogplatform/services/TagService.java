package com.example.blogplatform.services;

import com.example.blogplatform.domain.dtos.CreateTagRequest;
import com.example.blogplatform.domain.dtos.TagDto;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.UUID;

public interface TagService {

    List<TagDto> getAllTags();

    TagDto createTag(CreateTagRequest createTagRequest);

    TagDto deleteTag(UUID id);

    @ApiStatus.ScheduledForRemoval(inVersion = "1.1")
    @Deprecated(since = "1.1")
    TagDto getTagById(UUID id);
}
