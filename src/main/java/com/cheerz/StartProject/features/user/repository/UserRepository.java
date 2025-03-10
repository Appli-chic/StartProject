package com.cheerz.StartProject.features.user.repository;

import com.cheerz.StartProject.entity.UserEntity;
import com.cheerz.StartProject.features.user.exception.UserNameAlreadyExistsException;

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
    void updateUserName(@NotNull Long userId, @NotNull String name) throws UserNameAlreadyExistsException;

    @NotNull
    Optional<UserEntity> getUserById(@NotNull Long userId);

    @Transactional
    @NotNull
    UserEntity save(@NotNull String name, @NotNull Integer age) throws UserNameAlreadyExistsException;

    @Transactional
    void rentBook(@NotNull Long userId, @NotNull Long bookId);
}
