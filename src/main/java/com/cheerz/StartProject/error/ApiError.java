package com.cheerz.StartProject.error;

import jakarta.validation.constraints.NotNull;

public record ApiError(
    @NotNull
    String errorMessage,
    @NotNull
    int status
) {
}
