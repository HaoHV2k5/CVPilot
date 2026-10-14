package com.cvpilot.botagent.mapper;

import com.cvpilot.botagent.dto.RegisterRequest;
import com.cvpilot.botagent.dto.UserResponse;
import com.cvpilot.botagent.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User toEntity(RegisterRequest request) {
        if (request == null) {
            return null;
        }
        User user = new User();
        user.setEmail(request.getEmail().trim().toLowerCase());
        user.setFullName(request.getFullName().trim());
        user.setRole("USER");
        user.setStatus("ACTIVE");
        return user;
    }

    public UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }
        return modelMapper.map(user, UserResponse.class);
    }
}
