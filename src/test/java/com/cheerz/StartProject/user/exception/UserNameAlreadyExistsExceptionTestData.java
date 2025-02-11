package com.cheerz.StartProject.user.exception;

import static com.cheerz.StartProject.user.entity.UserEntity.UNIQUE_NAME_CONSTRAINT;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.SQLException;

public class UserNameAlreadyExistsExceptionTestData {
    private static final SQLException SQL_EXCEPTION = new SQLException("SQL exception");

    private static final ConstraintViolationException UNIQUE_CONSTRAINT_VIOLATION = new ConstraintViolationException(
        "Unique constraint violation",
        SQL_EXCEPTION,
        UNIQUE_NAME_CONSTRAINT
    );

    public static final DataIntegrityViolationException DATA_INTEGRITY_VIOLATION_EXCEPTION = new DataIntegrityViolationException(
        "Could not execute statement",
        UNIQUE_CONSTRAINT_VIOLATION
    );

    public static final UserNameAlreadyExistsException = new UserNameAlreadyExistsException("User name already exists");
}
