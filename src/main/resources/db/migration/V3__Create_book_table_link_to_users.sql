CREATE TABLE books
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    isbn VARCHAR(255)                            NOT NULL,
    name VARCHAR(255)                            NOT NULL,
    CONSTRAINT pk_books PRIMARY KEY (id)
);

CREATE TABLE users_books
(
    user_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    CONSTRAINT pk_users_books PRIMARY KEY (user_id, book_id)
);

ALTER TABLE books
    ADD CONSTRAINT uc_books_isbn UNIQUE (isbn);

ALTER TABLE users_books
    ADD CONSTRAINT FK_USERS_BOOKS_ON_BOOK FOREIGN KEY (book_id) REFERENCES books (id);

ALTER TABLE users_books
    ADD CONSTRAINT FK_USERS_BOOKS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);