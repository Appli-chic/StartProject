package com.cheerz.StartProject.library.repository;

import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@Repository
public interface RentRepository {
    @Transactional
    void rentBook(@NotNull Long userId, @NotNull Long bookId);
}
