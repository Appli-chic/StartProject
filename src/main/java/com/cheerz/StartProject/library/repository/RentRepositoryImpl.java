package com.cheerz.StartProject.library.repository;

import com.cheerz.StartProject.library.entity.UserBookEntity;
import com.cheerz.StartProject.library.entity.keys.UserBookKey;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@Repository
public class RentRepositoryImpl implements RentRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void rentBook(@NotNull Long userId, @NotNull Long bookId) {
        var userBookEntity = new UserBookEntity(new UserBookKey(userId, bookId));
        entityManager.persist(userBookEntity);
    }
}
