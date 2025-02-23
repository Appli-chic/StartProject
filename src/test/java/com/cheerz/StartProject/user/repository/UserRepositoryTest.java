package com.cheerz.StartProject.user.repository;

import static com.cheerz.StartProject.user.entity.UserEntityTestData.BOB_JOHNSON_USER_ENTITY;
import static com.cheerz.StartProject.user.entity.UserEntityTestData.JANE_SMITH_USER_ENTITY;
import static com.cheerz.StartProject.user.entity.UserEntityTestData.JOHN_DOE_USER_ENTITY;
import static com.cheerz.StartProject.user.entity.UserEntityTestData.MIKE_SMITH_TO_SAVE_USER_ENTITY;
import static org.assertj.core.api.Assertions.assertThat;

import com.cheerz.StartProject.user.entity.UserEntity;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(UserRepositoryImpl.class)
public class UserRepositoryTest {
    @Autowired
    private UserRepositoryImpl userRepository;

    @Test
    void getAllUsers_ShouldGetAllUsersInCreationOrder() {
        List<UserEntity> allUsers = userRepository.getAllUsers();

        assertThat(allUsers)
            .usingRecursiveComparison()
            .isEqualTo(List.of(BOB_JOHNSON_USER_ENTITY, JANE_SMITH_USER_ENTITY, JOHN_DOE_USER_ENTITY));
    }

    @Test
    void updateUserName_ShouldUpdateUserName() {
        String newName = "New Name";
        userRepository.updateUserName(BOB_JOHNSON_USER_ENTITY.getId(), newName);
        UserEntity updatedUser = userRepository.getUserById(BOB_JOHNSON_USER_ENTITY.getId()).orElseThrow();

        assertThat(updatedUser.getName()).isEqualTo(newName);
        assertThat(updatedUser)
            .usingRecursiveComparison()
            .ignoringFields("name")
            .isEqualTo(BOB_JOHNSON_USER_ENTITY);
    }

    @Test
    void saveUser_ShouldSaveUser() {
        UserEntity createdUser = userRepository.save(
            MIKE_SMITH_TO_SAVE_USER_ENTITY.getName(),
            MIKE_SMITH_TO_SAVE_USER_ENTITY.getAge()
        );

        assertThat(createdUser.getName()).isEqualTo(MIKE_SMITH_TO_SAVE_USER_ENTITY.getName());
        assertThat(createdUser.getAge()).isEqualTo(MIKE_SMITH_TO_SAVE_USER_ENTITY.getAge());
    }

    @Nested
    class GetUserByIdTests {
        @Test
        void shouldGetSpecificUser() {
            UserEntity bobJohnsonUserEntity = userRepository
                .getUserById(BOB_JOHNSON_USER_ENTITY.getId())
                .orElseThrow();

            assertThat(bobJohnsonUserEntity)
                .usingRecursiveComparison()
                .isEqualTo(BOB_JOHNSON_USER_ENTITY);
        }

        @Test
        void shouldGetEmptyUserIfUserNotFound() {
            Optional<UserEntity> unknownUserEntity = userRepository.getUserById(-1L);
            assertThat(unknownUserEntity).isEmpty();
        }
    }
}
