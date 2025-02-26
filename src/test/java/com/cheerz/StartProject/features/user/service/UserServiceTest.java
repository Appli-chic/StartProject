package com.cheerz.StartProject.features.user.service;

import static com.cheerz.StartProject.features.user.dto.ApiUserTestData.JANE_SMITH_USER_RESPONSE;
import static com.cheerz.StartProject.features.user.dto.ApiUserTestData.JOHN_DOE_USER_RESPONSE;
import static com.cheerz.StartProject.features.user.dto.CreateUserRequestTestData.JOHN_DOE_USER_REQUEST;
import static com.cheerz.StartProject.features.user.entity.UserEntityTestData.JANE_SMITH_USER_ENTITY;
import static com.cheerz.StartProject.features.user.entity.UserEntityTestData.JOHN_DOE_USER_ENTITY;
import static com.cheerz.StartProject.features.user.exception.UserNameAlreadyExistsExceptionTestData.USER_NAME_ALREADY_EXISTS_EXCEPTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.cheerz.StartProject.features.user.entity.UserEntityTestData;
import com.cheerz.StartProject.features.user.exception.UserNameAlreadyExistsException;
import com.cheerz.StartProject.features.user.repository.UserRepository;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void getAllUsers_ShouldReturnResponseWithAllUsers() {
        var expectedApiUserList = List.of(JOHN_DOE_USER_RESPONSE, JANE_SMITH_USER_RESPONSE);
        var userEntityList = List.of(JOHN_DOE_USER_ENTITY, JANE_SMITH_USER_ENTITY);
        when(userRepository.getAllUsers()).thenReturn(userEntityList);

        var apiUserList = userService.getAllUsers();

        assertEquals(expectedApiUserList, apiUserList);
    }

    @Nested
    class CreateUserName {
        @Test
        void shouldReturnCreatedUserResponse() {
            when(userRepository.save(JOHN_DOE_USER_REQUEST.name(), JOHN_DOE_USER_REQUEST.age()))
                .thenReturn(JOHN_DOE_USER_ENTITY);

            var apiUser = userService.createUser(JOHN_DOE_USER_REQUEST);

            assertEquals(JOHN_DOE_USER_RESPONSE, apiUser);
        }

        @Test
        void shouldThrowUserNameAlreadyExistsException() {
            when(userRepository.save(JOHN_DOE_USER_REQUEST.name(), JOHN_DOE_USER_REQUEST.age()))
                .thenThrow(USER_NAME_ALREADY_EXISTS_EXCEPTION);

            assertThrows(UserNameAlreadyExistsException.class, () ->
                userService.createUser(JOHN_DOE_USER_REQUEST)
            );
        }
    }

    @Nested
    class UpdateUserName {
        @Test
        void shouldUpdateUserName() {
            var newName = "New Name";
            var expectedUser = UserEntityTestData.copyWithNewName(JOHN_DOE_USER_ENTITY, newName);

            when(userRepository.getUserById(JOHN_DOE_USER_ENTITY.getId())).thenReturn(Optional.of(expectedUser));

            var newUser = userService.updateUserName(JOHN_DOE_USER_ENTITY.getId(), newName);

            assertThat(newUser)
                .usingRecursiveComparison()
                .isEqualTo(expectedUser);
        }

        @Test
        void shouldThrowEntityNotFoundException() {
            when(userRepository.getUserById(-1L)).thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class, () ->
                userService.updateUserName(-1L, "New Name")
            );
        }

        @Test
        void shouldThrowUserNameAlreadyExistsException() {
            doThrow(USER_NAME_ALREADY_EXISTS_EXCEPTION)
                .when(userRepository)
                .updateUserName(any(Long.class), any(String.class));

            assertThrows(UserNameAlreadyExistsException.class, () ->
                userService.updateUserName(-1L, "New Name")
            );
        }
    }
}
