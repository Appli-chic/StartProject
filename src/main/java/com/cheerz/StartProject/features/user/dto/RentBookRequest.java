package com.cheerz.StartProject.features.user.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RentBookRequest(
    @NotNull
    @Min(1)
    Long userId,
    @NotNull
    @Min(1)
    Long bookId
) {
}
