package com.cheerz.StartProject.user.entity;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class UserEntityTestData {

    public static final UserEntity JOHN_DOE_USER_ENTITY = new UserEntity(
        1L,
        "John Doe",
        25,
        LocalDateTime.of(2024, 2, 15, 10, 30, 0).toInstant(ZoneOffset.UTC),
        LocalDateTime.of(2024, 3, 11, 15, 45, 0).toInstant(ZoneOffset.UTC)
    );

    public static final UserEntity JANE_SMITH_USER_ENTITY = new UserEntity(
        2L,
        "Jane Smith",
        30,
        LocalDateTime.of(2024, 2, 20, 8, 15, 0).toInstant(ZoneOffset.UTC),
        null
    );

    public static final UserEntity BOB_JOHNSON_USER_ENTITY = new UserEntity(
        3L,
        "Bob Johnson",
        45,
        LocalDateTime.of(2024, 3, 16, 12, 0, 0).toInstant(ZoneOffset.UTC),
        LocalDateTime.of(2024, 3, 14, 9, 20, 0).toInstant(ZoneOffset.UTC)
    );

    @NotNull
    public static UserEntity copyWithNewName(@NotNull UserEntity original, @NotNull String newName) {
        return new UserEntity(
            original.getId(),
            newName,
            original.getAge()
        );
    }
}
