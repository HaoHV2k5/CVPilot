package com.cvpilot.botagent.service;

import com.cvpilot.botagent.dto.AuthResponse;
import com.cvpilot.botagent.dto.LoginRequest;
import com.cvpilot.botagent.dto.RegisterRequest;
import com.cvpilot.botagent.dto.UserResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    UserResponse getCurrentUser(String email);
}
