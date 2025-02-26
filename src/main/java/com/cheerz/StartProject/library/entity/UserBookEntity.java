package com.cheerz.StartProject.library.entity;

import com.cheerz.StartProject.library.entity.keys.UserBookKey;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "users_books")
public class UserBookEntity {
    @EmbeddedId
    private UserBookKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    protected UserBookEntity() { }

    public UserBookEntity(UserBookKey id) {
        this.id = id;
    }

}
