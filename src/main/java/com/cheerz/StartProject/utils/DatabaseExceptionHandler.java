package com.cheerz.StartProject.utils;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

public class DatabaseExceptionHandler {
    @NotNull
    public static boolean isConstraintViolation(
        @NotNull DataIntegrityViolationException dataIntegrityViolationException,
        @NotNull String constraintName
    ) {
        Throwable cause = dataIntegrityViolationException.getCause();
        if (cause instanceof ConstraintViolationException hibernateException) {
            return Objects.equals(hibernateException.getConstraintName(), constraintName);
        }

        return false;
    }
}
