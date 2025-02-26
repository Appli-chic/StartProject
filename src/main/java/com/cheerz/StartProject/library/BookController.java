package com.cheerz.StartProject.library;

import com.cheerz.StartProject.library.dto.CreateBookRequest;
import com.cheerz.StartProject.library.entity.BookEntity;
import com.cheerz.StartProject.library.repository.BookRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostMapping()
    ResponseEntity<BookEntity> addBook(@NotNull @Valid @RequestBody CreateBookRequest createBookRequest) {
        var bookEntity = bookRepository.addBook(createBookRequest.isbn(), createBookRequest.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(bookEntity);
    }
}
