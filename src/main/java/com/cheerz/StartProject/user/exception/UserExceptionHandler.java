package com.cheerz.StartProject.user.exception;

import com.cheerz.StartProject.error.ApiError;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;

@ControllerAdvice
public class UserExceptionHandler {
    @NotNull
    @ExceptionHandler(UserNameAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleUserNameAlreadyExistsException(
        @NotNull UserNameAlreadyExistsException userNameAlreadyExistsException
    ) {
        var error = new ApiError(
            userNameAlreadyExistsException.getMessage(),
            HttpStatus.CONFLICT.value()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    // TODO: Manage with a business model exception
    @NotNull
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFoundException(@NotNull EntityNotFoundException entityNotFoundException) {
        var error = new ApiError(
            entityNotFoundException.getMessage(),
            HttpStatus.CONFLICT.value()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}
