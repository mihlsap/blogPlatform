package com.example.blogplatform.services;

import com.example.blogplatform.domain.dtos.AuthorDto;

import java.util.UUID;

public interface AuthorService {
    AuthorDto getAuthorById(UUID id);
}
