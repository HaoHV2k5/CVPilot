package com.cvpilot.botagent.service;

import com.cvpilot.botagent.dto.AuthResponse;
import com.cvpilot.botagent.dto.LoginRequest;
import com.cvpilot.botagent.dto.RegisterRequest;
import com.cvpilot.botagent.entity.User;
import com.cvpilot.botagent.mapper.UserMapper;
import com.cvpilot.botagent.repository.UserRepository;
import com.cvpilot.botagent.security.JwtTokenProvider;
import com.cvpilot.botagent.service.impl.AuthServiceImpl;
import com.cvpilot.common.enums.ErrorCode;
import com.cvpilot.common.exception.BadRequestException;
import com.cvpilot.common.exception.ForbiddenException;
import com.cvpilot.common.exception.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    private UserMapper userMapper;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
        jwtTokenProvider = new JwtTokenProvider("super_secret_jwt_key_that_is_at_least_32_bytes_long_for_tests", 86400000L);
        userMapper = new UserMapper(new ModelMapper());
        authService = new AuthServiceImpl(userRepository, userMapper, passwordEncoder, jwtTokenProvider);
    }

    @Test
    void register_success() {
        RegisterRequest request = new RegisterRequest("test@example.com", "Password123!", "Nguyen Van A");

        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(UUID.randomUUID());
            return user;
        });

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertNotNull(response.getAccessToken());
        assertEquals("Bearer", response.getTokenType());
        assertEquals("test@example.com", response.getUser().getEmail());
        assertEquals("Nguyen Van A", response.getUser().getFullName());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_emailAlreadyExists_shouldThrowBadRequestException() {
        RegisterRequest request = new RegisterRequest("exists@example.com", "Password123!", "User");
        when(userRepository.existsByEmail("exists@example.com")).thenReturn(true);

        BadRequestException ex = assertThrows(BadRequestException.class, () -> authService.register(request));
        assertEquals(ErrorCode.EMAIL_ALREADY_EXISTS, ex.getErrorCode());
        verify(userRepository, never()).save(any());
    }

    @Test
    void login_success() {
        LoginRequest request = new LoginRequest("test@example.com", "Password123!");
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("test@example.com");
        user.setFullName("Nguyen Van A");
        user.setPasswordHash(passwordEncoder.encode("Password123!"));
        user.setStatus("ACTIVE");
        user.setRole("USER");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertNotNull(response.getAccessToken());
        assertEquals("test@example.com", response.getUser().getEmail());
    }

    @Test
    void login_wrongPassword_shouldThrowUnauthorizedException() {
        LoginRequest request = new LoginRequest("test@example.com", "WrongPassword");
        User user = new User();
        user.setEmail("test@example.com");
        user.setPasswordHash(passwordEncoder.encode("CorrectPassword"));
        user.setStatus("ACTIVE");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        UnauthorizedException ex = assertThrows(UnauthorizedException.class, () -> authService.login(request));
        assertEquals(ErrorCode.INVALID_CREDENTIALS, ex.getErrorCode());
    }

    @Test
    void login_lockedUser_shouldThrowForbiddenException() {
        LoginRequest request = new LoginRequest("locked@example.com", "Password123!");
        User user = new User();
        user.setEmail("locked@example.com");
        user.setStatus("LOCKED");

        when(userRepository.findByEmail("locked@example.com")).thenReturn(Optional.of(user));

        ForbiddenException ex = assertThrows(ForbiddenException.class, () -> authService.login(request));
        assertEquals(ErrorCode.USER_LOCKED, ex.getErrorCode());
    }
}
