package com.cheerz.StartProject.user.mapper;

import static com.cheerz.StartProject.user.dto.ApiUserTestData.JOHN_DOE_USER_RESPONSE;
import static com.cheerz.StartProject.user.entity.UserEntityTestData.JOHN_DOE_USER_ENTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UserMapperTest {
    private final UserMapper userMapper = new UserMapper();

    @Test
    void toUserResponse_ShouldMapUserEntityToUserResponse() {
        var apiUser = userMapper.toUserResponse(JOHN_DOE_USER_ENTITY);
        assertEquals(JOHN_DOE_USER_RESPONSE, apiUser);
    }
}
