package com.cheerz.StartProject.features.user.dto;

import jakarta.validation.constraints.NotNull;

public class ApiUserTestData {
    public static final ApiUser JOHN_DOE_USER_RESPONSE = new ApiUser(1L, "John Doe", 25);
    public static final ApiUser JANE_SMITH_USER_RESPONSE = new ApiUser(2L, "Jane Smith", 30);

    @NotNull
    public static ApiUser copyWithNewName(@NotNull ApiUser original, @NotNull String newName) {
        return new ApiUser(
            original.id(),
            newName,
            original.age()
        );
    }
}
