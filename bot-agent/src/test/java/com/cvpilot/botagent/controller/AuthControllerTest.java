package com.cvpilot.botagent.controller;

import com.cvpilot.botagent.dto.AuthResponse;
import com.cvpilot.botagent.dto.LoginRequest;
import com.cvpilot.botagent.dto.RegisterRequest;
import com.cvpilot.botagent.dto.UserResponse;
import com.cvpilot.botagent.exception.GlobalExceptionHandler;
import com.cvpilot.botagent.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private AuthService authService;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(new AuthController(authService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void register_validRequest_shouldReturn201() throws Exception {
        RegisterRequest request = new RegisterRequest("candidate@example.com", "Password123!", "Nguyen Van A");
        UserResponse user = new UserResponse(UUID.randomUUID(), "candidate@example.com", "Nguyen Van A", "USER", "ACTIVE", Instant.now());
        AuthResponse authResponse = AuthResponse.of("mock-token", 86400L, user);

        when(authService.register(any(RegisterRequest.class))).thenReturn(authResponse);

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(201))
                .andExpect(jsonPath("$.data.accessToken").value("mock-token"))
                .andExpect(jsonPath("$.data.user.email").value("candidate@example.com"));
    }

    @Test
    void register_invalidEmail_shouldReturn400() throws Exception {
        RegisterRequest request = new RegisterRequest("invalid-email", "Password123!", "Nguyen Van A");

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.errorCode").value("BAD_REQUEST"));
    }

    @Test
    void login_validRequest_shouldReturn200() throws Exception {
        LoginRequest request = new LoginRequest("candidate@example.com", "Password123!");
        UserResponse user = new UserResponse(UUID.randomUUID(), "candidate@example.com", "Nguyen Van A", "USER", "ACTIVE", Instant.now());
        AuthResponse authResponse = AuthResponse.of("mock-token", 86400L, user);

        when(authService.login(any(LoginRequest.class))).thenReturn(authResponse);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.accessToken").value("mock-token"));
    }
}
