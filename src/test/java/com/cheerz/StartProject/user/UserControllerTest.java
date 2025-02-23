package com.cheerz.StartProject.user;

import static com.cheerz.StartProject.user.dto.ApiUserTestData.JOHN_DOE_USER_RESPONSE;
import static com.cheerz.StartProject.user.dto.CreateUserRequestTestData.JOHN_DOE_USER_REQUEST;
import static com.cheerz.StartProject.user.dto.UpdateNameUserRequestTestData.NEW_NAME_USER_REQUEST;
import static com.cheerz.StartProject.user.exception.UserNameAlreadyExistsExceptionTestData.USER_NAME_ALREADY_EXISTS_EXCEPTION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cheerz.StartProject.user.dto.ApiUser;
import com.cheerz.StartProject.user.dto.ApiUserTestData;
import com.cheerz.StartProject.user.dto.CreateUserRequest;
import com.cheerz.StartProject.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.List;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void getAllUsers_ShouldReturnListOfUsers() throws Exception {
        List<ApiUser> apiUserList = List.of(JOHN_DOE_USER_RESPONSE);
        when(userService.getAllUsers()).thenReturn(apiUserList);
        String expectedResponse = new ObjectMapper().writeValueAsString(apiUserList);

        mockMvc.perform(get("/users"))
            .andExpect(status().isOk())
            .andExpect(content().json(expectedResponse));
    }

    @Nested
    class CreateUser {
        @Test
        void shouldReturnCreatedUser() throws Exception {
            ApiUser apiUser = JOHN_DOE_USER_RESPONSE;
            when(userService.createUser(any(CreateUserRequest.class))).thenReturn(apiUser);
            String expectedResponse = new ObjectMapper().writeValueAsString(apiUser);

            String createUserRequest = new ObjectMapper().writeValueAsString(JOHN_DOE_USER_REQUEST);
            RequestBuilder requestBuilder = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createUserRequest);

            mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedResponse));
        }

        @Test
        void shouldDisplayUserNameAlreadyExists() throws Exception {
            when(userService.createUser(any(CreateUserRequest.class))).thenThrow(USER_NAME_ALREADY_EXISTS_EXCEPTION);

            String createUserRequest = new ObjectMapper().writeValueAsString(JOHN_DOE_USER_REQUEST);
            RequestBuilder requestBuilder = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createUserRequest);

            mockMvc.perform(requestBuilder)
                .andExpect(status().isConflict());
        }
    }

    @Nested
    class UpdateUser {
        @Test
        void shouldReturnUserUpdated() throws Exception {
            ApiUser expectedApiUser = ApiUserTestData.copyWithNewName(JOHN_DOE_USER_RESPONSE, NEW_NAME_USER_REQUEST.name());

            when(userService.updateUserName(expectedApiUser.id(), NEW_NAME_USER_REQUEST.name())).thenReturn(expectedApiUser);
            String expectedResponse = new ObjectMapper().writeValueAsString(expectedApiUser);

            String updateUserNameRequest = new ObjectMapper().writeValueAsString(NEW_NAME_USER_REQUEST);
            RequestBuilder requestBuilder = patch("/users/{user_id}", expectedApiUser.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateUserNameRequest);

            mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
        }

        @Test
        void shouldDisplayUserNameAlreadyExists() throws Exception {
            when(userService.updateUserName(any(Long.class), any(String.class))).thenThrow(USER_NAME_ALREADY_EXISTS_EXCEPTION);

            String updateUserNameRequest = new ObjectMapper().writeValueAsString(NEW_NAME_USER_REQUEST);
            RequestBuilder requestBuilder = patch("/users/{user_id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateUserNameRequest);

            mockMvc.perform(requestBuilder)
                .andExpect(status().isConflict());
        }
    }
}
