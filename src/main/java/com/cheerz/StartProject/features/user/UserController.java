package com.cheerz.StartProject.features.user;

import com.cheerz.StartProject.features.user.dto.ApiUser;
import com.cheerz.StartProject.features.user.dto.CreateUserRequest;
import com.cheerz.StartProject.features.user.dto.UpdateNameUserRequest;
import com.cheerz.StartProject.features.user.exception.UserNameAlreadyExistsException;
import com.cheerz.StartProject.features.user.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    List<ApiUser> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping()
    ResponseEntity<ApiUser> createUser(@NotNull @Valid @RequestBody CreateUserRequest createUserRequest) throws UserNameAlreadyExistsException {
        var apiUser = userService.createUser(createUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiUser);
    }

    @PatchMapping("/{userId}")
    ResponseEntity<ApiUser> updateUserName(
        @NotNull @PathVariable Long userId,
        @NotNull @Valid @RequestBody UpdateNameUserRequest updateNameUserRequest
    ) throws EntityNotFoundException, UserNameAlreadyExistsException {
        var apiUser = userService.updateUserName(userId, updateNameUserRequest.name());
        return ResponseEntity.ok(apiUser);
    }

//    @PostMapping("/book")
//    public ResponseEntity rentBook(@NotNull @Valid @RequestBody RentBookRequest rentBookRequest) {
//        rentRepository.rentBook(rentBookRequest.userId(), rentBookRequest.bookId());
//        return ResponseEntity.status(HttpStatus.CREATED).body(null);
//    }
}
