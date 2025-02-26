package com.cheerz.StartProject.library.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateNameUserRequest(
    @NotNull
    @Size(min = 1, max = 255)
    String name
) { }
