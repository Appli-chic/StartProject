package com.cheerz.StartProject.user.mapper;

import com.cheerz.StartProject.user.dto.CreateUserRequest;
import com.cheerz.StartProject.user.dto.ApiUser;
import com.cheerz.StartProject.user.entity.UserEntity;
import jakarta.validation.constraints.NotNull;

public class UserMapper {
    @NotNull
    public ApiUser toUserResponse(@NotNull UserEntity userEntity) {
        return new ApiUser(userEntity.getId(), userEntity.getName(), userEntity.getAge());
    }

    @NotNull
    public UserEntity toEntity(@NotNull CreateUserRequest createUserRequest) {
        return new UserEntity(createUserRequest.name(), createUserRequest.age());
    }
}
