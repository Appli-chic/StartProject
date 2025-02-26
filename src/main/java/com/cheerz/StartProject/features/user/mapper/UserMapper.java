package com.cheerz.StartProject.features.user.mapper;

import com.cheerz.StartProject.entity.UserEntity;
import com.cheerz.StartProject.features.user.dto.ApiUser;

import jakarta.validation.constraints.NotNull;

public class UserMapper {
    @NotNull
    public ApiUser toUserResponse(@NotNull UserEntity userEntity) {
        return new ApiUser(userEntity.getId(), userEntity.getName(), userEntity.getAge());
    }
}
