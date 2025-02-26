package com.cheerz.StartProject.library;

import com.cheerz.StartProject.library.dto.RentBookRequest;
import com.cheerz.StartProject.library.repository.RentRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/renting")
public class RentController {
    private final RentRepository rentRepository;

    public RentController(RentRepository rentRepository) {
        this.rentRepository = rentRepository;
    }

    @GetMapping("/books")
    public void getRentedBooks() {

    }

    @PostMapping("/book")
    public ResponseEntity rentBook(@NotNull @Valid @RequestBody RentBookRequest rentBookRequest) {
        rentRepository.rentBook(rentBookRequest.userId(), rentBookRequest.bookId());
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
