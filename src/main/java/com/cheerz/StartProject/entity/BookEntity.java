package com.cheerz.StartProject.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "book")
    Set<UserBookEntity> userBooks;

    public BookEntity() {}

    public BookEntity(String isbn, String name) {
        this.isbn = isbn;
        this.name = name;
    }
}
