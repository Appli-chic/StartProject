package com.cheerz.StartProject.library.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {
    public static final String UNIQUE_NAME_CONSTRAINT = "unique_constraint_users_name";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

    @OneToMany(mappedBy = "user")
    Set<UserBookEntity> userBooks;

    protected UserEntity() {}

    public UserEntity(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public UserEntity(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public UserEntity(Long id, String name, Integer age, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }

    public String getName() { return name; }

    public Integer getAge() { return age; }

    public Instant getCreatedAt() { return createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
}
