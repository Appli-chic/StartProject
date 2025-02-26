package com.cheerz.StartProject.features.user.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
    @NotNull
    @Size(min = 1, max = 255)
    String name,

    @NotNull
    @Min(1)
    Integer age
) {}
