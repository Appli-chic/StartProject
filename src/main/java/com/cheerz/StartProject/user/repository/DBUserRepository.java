package com.cheerz.StartProject.user.repository;

import com.cheerz.StartProject.user.entity.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

@Repository
public interface DBUserRepository extends JpaRepository<UserEntity, Long>, UserRepository {
    @Query("SELECT user FROM UserEntity user ORDER BY user.createdAt DESC")
    @NotNull
    List<UserEntity> getAllUsers();

    @Modifying
    @Query("UPDATE UserEntity user SET user.name = :name WHERE user.id = :user_id")
    void updateUserName(@Param("user_id") Long userId, @Param("name") String name);

    @Query("SELECT user FROM UserEntity user WHERE user.id = :user_id")
    Optional<UserEntity> getUserById(@Param("user_id") Long userId);
}