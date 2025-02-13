package com.cheerz.StartProject.user.service;

import static com.cheerz.StartProject.user.entity.UserEntity.UNIQUE_NAME_CONSTRAINT;

import com.cheerz.StartProject.user.dto.ApiUser;
import com.cheerz.StartProject.user.dto.CreateUserRequest;
import com.cheerz.StartProject.user.entity.UserEntity;
import com.cheerz.StartProject.user.exception.UserNameAlreadyExistsException;
import com.cheerz.StartProject.user.mapper.UserMapper;
import com.cheerz.StartProject.user.repository.UserRepository;
import com.cheerz.StartProject.utils.DatabaseExceptionHandler;

import org.springframework.dao.DataIntegrityViolationException;
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
        try {
            UserEntity savedUser = userRepository.save(createUserRequest.name(), createUserRequest.age());
            return userMapper.toUserResponse(savedUser);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            if (DatabaseExceptionHandler.isConstraintViolation(dataIntegrityViolationException, UNIQUE_NAME_CONSTRAINT)) {
                throw new UserNameAlreadyExistsException(createUserRequest.name(), dataIntegrityViolationException);
            }

            throw dataIntegrityViolationException;
        }
    }

    @Transactional
    @NotNull
    public ApiUser updateUserName(@NotNull Long userId, @NotNull String name) throws EntityNotFoundException {
        try {
            userRepository.updateUserName(userId, name);
            UserEntity userEntity = userRepository.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id '" + userId + "' not found"));

            return userMapper.toUserResponse(userEntity);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            if (DatabaseExceptionHandler.isConstraintViolation(dataIntegrityViolationException, UNIQUE_NAME_CONSTRAINT)) {
                throw new UserNameAlreadyExistsException(name, dataIntegrityViolationException);
            }

            throw dataIntegrityViolationException;
        }
    }
}
