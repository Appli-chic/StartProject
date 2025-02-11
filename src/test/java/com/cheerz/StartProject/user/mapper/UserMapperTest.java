package com.cheerz.StartProject.user.mapper;

import com.cheerz.StartProject.user.dto.ApiUser;
import com.cheerz.StartProject.user.entity.UserEntity;
import org.junit.jupiter.api.Test;

import static com.cheerz.StartProject.user.dto.CreateUserRequestTestData.JOHN_DOE_USER_REQUEST;
import static com.cheerz.StartProject.user.dto.ApiUserTestData.JOHN_DOE_USER_RESPONSE;
import static com.cheerz.StartProject.user.entity.UserEntityTestData.JOHN_DOE_USER_ENTITY;
import static com.cheerz.StartProject.user.entity.UserEntityTestData.SIMPLE_JOHN_DOE_USER_ENTITY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {
    private final UserMapper userMapper = new UserMapper();

    @Test
    void toUserResponse_ShouldMapUserEntityToUserResponse() {
        ApiUser apiUser = userMapper.toUserResponse(JOHN_DOE_USER_ENTITY);
        assertEquals(JOHN_DOE_USER_RESPONSE, apiUser);
    }

    @Test
    void toEntity_ShouldMapCreateUserRequestToUserEntity() {
        UserEntity userEntity = userMapper.toEntity(JOHN_DOE_USER_REQUEST);
        assertThat(userEntity)
            .usingRecursiveComparison()
            .isEqualTo(SIMPLE_JOHN_DOE_USER_ENTITY);
    }
}
