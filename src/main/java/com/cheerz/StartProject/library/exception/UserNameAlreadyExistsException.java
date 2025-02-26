package com.cheerz.StartProject.library.exception;

import jakarta.validation.constraints.NotNull;

public class UserNameAlreadyExistsException extends RuntimeException {
    public UserNameAlreadyExistsException(@NotNull String name, @NotNull Throwable cause) {
        super(
            "User with name '" + name + "' already exists",
            cause
        );
    }
}
