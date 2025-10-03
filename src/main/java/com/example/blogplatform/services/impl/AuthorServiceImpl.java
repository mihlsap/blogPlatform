package com.example.blogplatform.services.impl;

import com.example.blogplatform.domain.dtos.AuthorDto;
import com.example.blogplatform.mappers.AuthorMapper;
import com.example.blogplatform.repositories.UserRepository;
import com.example.blogplatform.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.ApiStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@ApiStatus.ScheduledForRemoval(inVersion = "1.1")
@Deprecated(since = "1.1")

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final UserRepository userRepository;
    private final AuthorMapper authorMapper;

    @Override
    public AuthorDto getAuthorById(UUID id) {
        return authorMapper.toDto(userRepository.findById(id).orElse(null));
    }
}
