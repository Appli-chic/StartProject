package com.cheerz.StartProject.user.exception;

import org.springframework.dao.DataIntegrityViolationException;

import jakarta.validation.constraints.NotNull;

// TODO: Does it need to extends from DataIntegrityViolationException ?
public class UserNameAlreadyExistsException extends DataIntegrityViolationException {
    public UserNameAlreadyExistsException(@NotNull String name, @NotNull Throwable cause) {
        super(
            "User with name '" + name + "' already exists",
            cause
        );
    }
}
