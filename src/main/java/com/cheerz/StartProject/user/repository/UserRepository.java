package com.cheerz.StartProject.user.repository;

import com.cheerz.StartProject.user.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<UserEntity> getAllUsers();
    void updateUserName(Long userId, String name);

    Optional<UserEntity> getUserById(Long userId);

    // TODO: Use CreateUserRequest
    UserEntity save(UserEntity entity);
}
