package com.cvpilot.botagent.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.UUID;

@Schema(description = "Thông tin chi tiết người dùng")
public class UserResponse {

    @Schema(description = "ID định danh người dùng", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(description = "Địa chỉ email", example = "candidate@example.com")
    private String email;

    @Schema(description = "Họ và tên đầy đủ", example = "Nguyễn Văn A")
    private String fullName;

    @Schema(description = "Vai trò người dùng trong hệ thống (USER, ADMIN)", example = "USER")
    private String role;

    @Schema(description = "Trạng thái tài khoản (ACTIVE, LOCKED)", example = "ACTIVE")
    private String status;

    @Schema(description = "Thời điểm tạo tài khoản")
    private Instant createdAt;

    public UserResponse() {
    }

    public UserResponse(UUID id, String email, String fullName, String role, String status, Instant createdAt) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
