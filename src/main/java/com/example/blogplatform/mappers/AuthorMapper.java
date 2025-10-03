package com.example.blogplatform.mappers;

import com.example.blogplatform.domain.dtos.AuthorDto;
import com.example.blogplatform.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthorMapper {
    AuthorDto toDto(User user);
}
