package com.cheerz.StartProject.user.repository;

import com.cheerz.StartProject.user.entity.UserEntity;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@Repository
public interface UserRepository {
    @NotNull
    List<UserEntity> getAllUsers();

    @Transactional
    void updateUserName(@NotNull Long userId, @NotNull String name);

    @NotNull
    Optional<UserEntity> getUserById(@NotNull Long userId);

    @Transactional
    @NotNull
    UserEntity save(@NotNull String name, @NotNull Integer age);
}
