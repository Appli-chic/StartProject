package com.cheerz.StartProject.error;

import org.springframework.http.HttpStatus;

public class ApiErrorTestData {
    public static ApiError getUsernameAlreadyExistApiError(Exception exception) {
        return getApiError(exception, HttpStatus.CONFLICT.value());
    }

    private static ApiError getApiError(Exception exception, int status) {
        return new ApiError(exception.getMessage(), status);
    }
}
