package com.cheerz.StartProject.user.repository;

import static com.cheerz.StartProject.user.entity.UserEntity.UNIQUE_NAME_CONSTRAINT;

import com.cheerz.StartProject.user.entity.UserEntity;
import com.cheerz.StartProject.user.exception.UserNameAlreadyExistsException;
import com.cheerz.StartProject.utils.DatabaseExceptionHandler;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @NotNull
    public List<UserEntity> getAllUsers() {
        return entityManager
            .createQuery("SELECT user FROM UserEntity user ORDER BY user.createdAt DESC", UserEntity.class)
            .getResultList();
    }

    @Transactional
    public void updateUserName(@NotNull Long userId, @NotNull String name) throws UserNameAlreadyExistsException {
        try {
            entityManager.createQuery("UPDATE UserEntity user SET user.name = :name WHERE user.id = :user_id")
                .setParameter("name", name)
                .setParameter("user_id", userId)
                .executeUpdate();
        } catch (ConstraintViolationException dataIntegrityViolationException) {
            handleUniqueNameException(dataIntegrityViolationException, name);
            throw dataIntegrityViolationException;
        }
    }

    @NotNull
    public Optional<UserEntity> getUserById(@NotNull Long userId) {
        try {
            var user = (UserEntity) entityManager.createQuery("SELECT user FROM UserEntity user WHERE user.id = :user_id")
                .setParameter("user_id", userId)
                .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Transactional
    @NotNull
    public UserEntity save(@NotNull String name, @NotNull Integer age) throws UserNameAlreadyExistsException {
        try {
            var user = new UserEntity(name, age);
            entityManager.persist(user);
            return user;
        } catch (ConstraintViolationException dataIntegrityViolationException) {
            handleUniqueNameException(dataIntegrityViolationException, name);
            throw dataIntegrityViolationException;
        }
    }

    private void handleUniqueNameException(@NotNull ConstraintViolationException e, @NotNull String name) throws UserNameAlreadyExistsException {
        if (DatabaseExceptionHandler.isConstraintViolation(e, UNIQUE_NAME_CONSTRAINT)) {
            throw new UserNameAlreadyExistsException(name, e);
        }
    }
}
