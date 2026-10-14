package com.cvpilot.botagent.service.impl;

import com.cvpilot.botagent.dto.AuthResponse;
import com.cvpilot.botagent.dto.LoginRequest;
import com.cvpilot.botagent.dto.RegisterRequest;
import com.cvpilot.botagent.dto.UserResponse;
import com.cvpilot.botagent.entity.User;
import com.cvpilot.botagent.mapper.UserMapper;
import com.cvpilot.botagent.repository.UserRepository;
import com.cvpilot.botagent.security.JwtTokenProvider;
import com.cvpilot.botagent.service.AuthService;
import com.cvpilot.common.enums.ErrorCode;
import com.cvpilot.common.exception.BadRequestException;
import com.cvpilot.common.exception.ForbiddenException;
import com.cvpilot.common.exception.ResourceNotFoundException;
import com.cvpilot.common.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String normalizedEmail = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(normalizedEmail)) {
            log.warn("Registration rejected: email already exists [{}]", normalizedEmail);
            throw new BadRequestException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        User user = userMapper.toEntity(request);
        user.setEmail(normalizedEmail);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);
        log.info("User registered successfully: [id: {}, email: {}]", savedUser.getId(), savedUser.getEmail());

        String token = jwtTokenProvider.generateToken(savedUser.getId(), savedUser.getEmail(), savedUser.getRole());
        UserResponse userResponse = userMapper.toResponse(savedUser);

        return AuthResponse.of(token, jwtTokenProvider.getExpirationInSeconds(), userResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        String normalizedEmail = request.getEmail().trim().toLowerCase();

        User user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> {
                    log.warn("Login failed: email not found [{}]", normalizedEmail);
                    return new UnauthorizedException(ErrorCode.INVALID_CREDENTIALS);
                });

        if (!"ACTIVE".equalsIgnoreCase(user.getStatus())) {
            log.warn("Login rejected: user is locked [{}]", normalizedEmail);
            throw new ForbiddenException(ErrorCode.USER_LOCKED);
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            log.warn("Login failed: invalid password for [{}]", normalizedEmail);
            throw new UnauthorizedException(ErrorCode.INVALID_CREDENTIALS);
        }

        String token = jwtTokenProvider.generateToken(user.getId(), user.getEmail(), user.getRole());
        UserResponse userResponse = userMapper.toResponse(user);
        log.info("User logged in successfully: [email: {}]", normalizedEmail);

        return AuthResponse.of(token, jwtTokenProvider.getExpirationInSeconds(), userResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getCurrentUser(String email) {
        User user = userRepository.findByEmail(email.trim().toLowerCase())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toResponse(user);
    }
}
