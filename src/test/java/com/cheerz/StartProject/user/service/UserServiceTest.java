package com.cheerz.StartProject.user.service;

import static com.cheerz.StartProject.user.dto.ApiUserTestData.JANE_SMITH_USER_RESPONSE;
import static com.cheerz.StartProject.user.dto.ApiUserTestData.JOHN_DOE_USER_RESPONSE;
import static com.cheerz.StartProject.user.dto.CreateUserRequestTestData.JOHN_DOE_USER_REQUEST;
import static com.cheerz.StartProject.user.entity.UserEntityTestData.JANE_SMITH_USER_ENTITY;
import static com.cheerz.StartProject.user.entity.UserEntityTestData.JOHN_DOE_USER_ENTITY;
import static com.cheerz.StartProject.user.exception.UserNameAlreadyExistsExceptionTestData.DATA_INTEGRITY_VIOLATION_EXCEPTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.cheerz.StartProject.user.dto.ApiUser;
import com.cheerz.StartProject.user.entity.UserEntity;
import com.cheerz.StartProject.user.entity.UserEntityTestData;
import com.cheerz.StartProject.user.exception.UserNameAlreadyExistsException;
import com.cheerz.StartProject.user.repository.UserRepository;

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
        List<ApiUser> expectedApiUserList = List.of(JOHN_DOE_USER_RESPONSE, JANE_SMITH_USER_RESPONSE);
        List<UserEntity> userEntityList = List.of(JOHN_DOE_USER_ENTITY, JANE_SMITH_USER_ENTITY);
        when(userRepository.getAllUsers()).thenReturn(userEntityList);

        List<ApiUser> apiUserList = userService.getAllUsers();

        assertEquals(expectedApiUserList, apiUserList);
    }

    @Nested
    class CreateUserName {
        @Test
        void shouldReturnCreatedUserResponse() {
            when(userRepository.save(JOHN_DOE_USER_REQUEST.name(), JOHN_DOE_USER_REQUEST.age()))
                .thenReturn(JOHN_DOE_USER_ENTITY);

            ApiUser apiUser = userService.createUser(JOHN_DOE_USER_REQUEST);

            assertEquals(JOHN_DOE_USER_RESPONSE, apiUser);
        }

        @Test
        void shouldThrowUserNameAlreadyExistsException() {
            when(userRepository.save(JOHN_DOE_USER_REQUEST.name(), JOHN_DOE_USER_REQUEST.age()))
                .thenThrow(DATA_INTEGRITY_VIOLATION_EXCEPTION);

            assertThrows(UserNameAlreadyExistsException.class, () ->
                userService.createUser(JOHN_DOE_USER_REQUEST)
            );
        }
    }

    @Nested
    class UpdateUserName {
        @Test
        void shouldUpdateUserName() {
            String newName = "New Name";
            UserEntity expectedUser = UserEntityTestData.copyWithNewName(JOHN_DOE_USER_ENTITY, newName);

            when(userRepository.getUserById(JOHN_DOE_USER_ENTITY.getId())).thenReturn(Optional.of(expectedUser));

            ApiUser newUser = userService.updateUserName(JOHN_DOE_USER_ENTITY.getId(), newName);

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
            doThrow(DATA_INTEGRITY_VIOLATION_EXCEPTION)
                .when(userRepository)
                .updateUserName(any(Long.class), any(String.class));

            assertThrows(UserNameAlreadyExistsException.class, () ->
                userService.updateUserName(-1L, "New Name")
            );
        }
    }
}
