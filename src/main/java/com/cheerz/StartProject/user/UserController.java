package com.cheerz.StartProject.user;

import com.cheerz.StartProject.user.dto.CreateUserRequest;
import com.cheerz.StartProject.user.dto.UpdateNameUserRequest;
import com.cheerz.StartProject.user.dto.ApiUser;
import com.cheerz.StartProject.user.exception.UserNameAlreadyExistsException;
import com.cheerz.StartProject.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
    ResponseEntity<ApiUser> createUser(@NotNull @Valid @RequestBody CreateUserRequest createUserRequest) {
        try {
            var apiUser = userService.createUser(createUserRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiUser);
        } catch (UserNameAlreadyExistsException userNameAlreadyExistsException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @PatchMapping("/{userId}")
    ResponseEntity<ApiUser> updateUserName(
        @NotNull @PathVariable Long userId,
        @NotNull @Valid @RequestBody UpdateNameUserRequest updateNameUserRequest
    ) {
        try {
            var apiUser = userService.updateUserName(userId, updateNameUserRequest.name());
            return ResponseEntity.ok(apiUser);
        } catch (UserNameAlreadyExistsException userNameAlreadyExistsException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }
}
