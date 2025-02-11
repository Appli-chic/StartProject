package com.cheerz.StartProject.user.dto;

import jakarta.validation.constraints.NotNull;

public record ApiUser(@NotNull Long id, @NotNull String name, int age) { }
