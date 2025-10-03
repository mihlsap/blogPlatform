package com.example.blogplatform.mappers;

import com.example.blogplatform.domain.PostStatus;
import com.example.blogplatform.domain.dtos.CreateTagRequest;
import com.example.blogplatform.domain.dtos.TagDto;
import com.example.blogplatform.domain.entities.Post;
import com.example.blogplatform.domain.entities.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {

    Tag toEntity(CreateTagRequest createRequest);

    @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
    TagDto toDto(Tag tag);

    @Named("calculatePostCount")
    default Integer calculatePostCount(Set<Post> posts) {
        if (posts == null) {
            return 0;
        }
        return (int) posts.stream()
                .filter(post ->
                        PostStatus.PUBLISHED.equals(post.getStatus())
                ).count();
    }
}
