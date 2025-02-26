package com.cheerz.StartProject.library.repository;

import static com.cheerz.StartProject.library.entity.UserEntityTestData.BOB_JOHNSON_USER_ENTITY;
import static com.cheerz.StartProject.library.entity.UserEntityTestData.JANE_SMITH_USER_ENTITY;
import static com.cheerz.StartProject.library.entity.UserEntityTestData.JOHN_DOE_USER_ENTITY;
import static com.cheerz.StartProject.library.entity.UserEntityTestData.MIKE_SMITH_TO_SAVE_USER_ENTITY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.cheerz.StartProject.library.exception.UserNameAlreadyExistsException;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(UserRepositoryImpl.class)
public class UserRepositoryTest {
    @Autowired
    private UserRepositoryImpl userRepository;

    @Test
    void getAllUsers_ShouldGetAllUsersInCreationOrder() {
        var allUsers = userRepository.getAllUsers();

        assertThat(allUsers)
            .usingRecursiveComparison()
            .isEqualTo(List.of(BOB_JOHNSON_USER_ENTITY, JANE_SMITH_USER_ENTITY, JOHN_DOE_USER_ENTITY));
    }

    @Nested
    class UpdateUserName {
        @Test
        void shouldUpdateUserName() {
            var newName = "New Name";
            userRepository.updateUserName(BOB_JOHNSON_USER_ENTITY.getId(), newName);
            var updatedUser = userRepository.getUserById(BOB_JOHNSON_USER_ENTITY.getId()).orElseThrow();

            assertThat(updatedUser.getName()).isEqualTo(newName);
            assertThat(updatedUser)
                .usingRecursiveComparison()
                .ignoringFields("name")
                .isEqualTo(BOB_JOHNSON_USER_ENTITY);
        }

        @Test
        void shouldThrowUserNameAlreadyExistsException() {
            assertThrows(UserNameAlreadyExistsException.class, () ->
                userRepository.updateUserName(BOB_JOHNSON_USER_ENTITY.getId(), JANE_SMITH_USER_ENTITY.getName())
            );
        }
    }

    @Nested
    class SaveUser {
        @Test
        void shouldSaveUser() {
            var createdUser = userRepository.save(
                MIKE_SMITH_TO_SAVE_USER_ENTITY.getName(),
                MIKE_SMITH_TO_SAVE_USER_ENTITY.getAge()
            );

            assertThat(createdUser.getName()).isEqualTo(MIKE_SMITH_TO_SAVE_USER_ENTITY.getName());
            assertThat(createdUser.getAge()).isEqualTo(MIKE_SMITH_TO_SAVE_USER_ENTITY.getAge());
        }

        @Test
        void shouldThrowUserNameAlreadyExistsException() {
            assertThrows(UserNameAlreadyExistsException.class, () ->
                userRepository.save(BOB_JOHNSON_USER_ENTITY.getName(), BOB_JOHNSON_USER_ENTITY.getAge())
            );
        }
    }

    @Nested
    class GetUserByIdTests {
        @Test
        void shouldGetSpecificUser() {
            var bobJohnsonUserEntity = userRepository
                .getUserById(BOB_JOHNSON_USER_ENTITY.getId())
                .orElseThrow();

            assertThat(bobJohnsonUserEntity)
                .usingRecursiveComparison()
                .isEqualTo(BOB_JOHNSON_USER_ENTITY);
        }

        @Test
        void shouldGetEmptyUserIfUserNotFound() {
            var unknownUserEntity = userRepository.getUserById(-1L);
            assertThat(unknownUserEntity).isEmpty();
        }
    }
}
