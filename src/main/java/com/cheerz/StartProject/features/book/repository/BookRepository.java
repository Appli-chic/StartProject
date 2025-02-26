package com.cheerz.StartProject.features.book.repository;

import com.cheerz.StartProject.entity.BookEntity;

import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@Repository
public interface BookRepository {
    @NotNull
    @Transactional
    BookEntity addBook(@NotNull String isbn, @NotNull String name);
}
