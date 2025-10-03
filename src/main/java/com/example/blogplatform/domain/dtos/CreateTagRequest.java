package com.example.blogplatform.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTagRequest {

    @NotBlank(message = "Tag name is required.")
    @Size(min = 2, max = 50, message = "Tag's name length must be between {min} and {max} characters")
    @Pattern(regexp = "^[\\w\\s.-]+$", message = "Tag name can only contain letters, numbers, spaces, underscores and hyphens")
    private String name;
}
