package com.cheerz.StartProject.library.repository;

import com.cheerz.StartProject.library.entity.BookEntity;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@Repository
public class BookRepositoryImpl implements BookRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @NotNull
    @Transactional
    public BookEntity addBook(@NotNull String isbn, @NotNull String name) {
        var book = new BookEntity(isbn, name);
        entityManager.persist(book);
        return book;
    }
}
