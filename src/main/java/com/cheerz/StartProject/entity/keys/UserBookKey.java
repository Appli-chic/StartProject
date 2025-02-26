package com.cheerz.StartProject.entity.keys;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class UserBookKey implements Serializable {
    private Long userId;
    private Long bookId;

    public UserBookKey(){}

    public UserBookKey(Long userId, Long bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }
}
