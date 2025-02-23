package com.cheerz.StartProject.utils;

import org.hibernate.exception.ConstraintViolationException;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

public class DatabaseExceptionHandler {
    @NotNull
    public static boolean isConstraintViolation(
        @NotNull ConstraintViolationException constraintViolationException,
        @NotNull String constraintName
    ) {
        return Objects.equals(constraintViolationException.getConstraintName(), constraintName);
    }
}
