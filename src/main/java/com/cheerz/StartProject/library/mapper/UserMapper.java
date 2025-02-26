package com.cheerz.StartProject.library.mapper;

import com.cheerz.StartProject.library.dto.ApiUser;
import com.cheerz.StartProject.library.entity.UserEntity;

import jakarta.validation.constraints.NotNull;

public class UserMapper {
    @NotNull
    public ApiUser toUserResponse(@NotNull UserEntity userEntity) {
        return new ApiUser(userEntity.getId(), userEntity.getName(), userEntity.getAge());
    }
}
