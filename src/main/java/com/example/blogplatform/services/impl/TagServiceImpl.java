package com.example.blogplatform.services.impl;

import com.example.blogplatform.domain.dtos.CreateTagRequest;
import com.example.blogplatform.domain.dtos.TagResponse;
import com.example.blogplatform.domain.entities.Category;
import com.example.blogplatform.domain.entities.Tag;
import com.example.blogplatform.mappers.TagMapper;
import com.example.blogplatform.repositories.TagRepository;
import com.example.blogplatform.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public List<TagResponse> getAllTags() {
        return tagRepository.findAllWithPostCount()
                .stream()
                .map(tagMapper::toTagResponse)
                .toList();
    }

    @Override
    public TagResponse createTag(CreateTagRequest createTagRequest) {
        if (tagRepository.existsByNameIgnoreCase(createTagRequest.getName())) {
            throw new IllegalArgumentException("Tag already exists with name: " + createTagRequest.getName());
        }
        return tagMapper.toTagResponse(tagRepository.save(tagMapper.toEntity(createTagRequest)));
    }

    @Override
    public TagResponse deleteTag(UUID id) {
        Optional<Tag> tag = tagRepository.findById(id);
        if (tag.isEmpty()) {
            throw new IllegalArgumentException("Tag with id: " + id + " not found");
        }
        if (!tag.get().getPosts().isEmpty()) {
            throw new IllegalStateException("Tag " + tag.get().getName() + " has posts associated with it");
        }
        tagRepository.deleteById(id);
        return null;
    }
}
