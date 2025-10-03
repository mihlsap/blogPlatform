package com.example.blogplatform.mappers;

import com.example.blogplatform.domain.dtos.PostDto;
import com.example.blogplatform.domain.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {
    PostDto toDto(Post post);
}
