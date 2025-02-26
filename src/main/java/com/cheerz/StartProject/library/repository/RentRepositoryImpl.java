package com.cheerz.StartProject.library.repository;

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
        entityManager.createNativeQuery("INSERT INTO users_books (user_id, book_id) VALUES (:userId, :bookId)")
            .setParameter("userId", userId)
            .setParameter("bookId", bookId)
            .executeUpdate();
    }
}
