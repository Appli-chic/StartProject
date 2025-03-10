package com.cheerz.StartProject.features.user.service;

import com.cheerz.StartProject.features.user.dto.ApiUser;
import com.cheerz.StartProject.features.user.dto.CreateUserRequest;
import com.cheerz.StartProject.features.user.exception.UserNameAlreadyExistsException;
import com.cheerz.StartProject.features.user.mapper.UserMapper;
import com.cheerz.StartProject.features.user.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper = new UserMapper();

    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }

    @NotNull
    public List<ApiUser> getAllUsers() {
        return userRepository.getAllUsers()
            .stream()
            .map(userMapper::toUserResponse)
            .collect(Collectors.toList());
    }

    @NotNull
    public ApiUser createUser(@NotNull CreateUserRequest createUserRequest) throws UserNameAlreadyExistsException {
        var savedUser = userRepository.save(createUserRequest.name(), createUserRequest.age());
        return userMapper.toUserResponse(savedUser);
    }

    @Transactional
    @NotNull
    public ApiUser updateUserName(@NotNull Long userId, @NotNull String name) throws EntityNotFoundException, UserNameAlreadyExistsException {
        userRepository.updateUserName(userId, name);
        var userEntity = userRepository.getUserById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User with id '" + userId + "' not found"));

        return userMapper.toUserResponse(userEntity);
    }

    public void rentBook(@NotNull Long userId, @NotNull Long bookId) {
        userRepository.rentBook(userId, bookId);
    }
}
