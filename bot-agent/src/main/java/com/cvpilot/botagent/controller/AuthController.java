package com.cvpilot.botagent.controller;

import com.cvpilot.botagent.dto.AuthResponse;
import com.cvpilot.botagent.dto.LoginRequest;
import com.cvpilot.botagent.dto.RegisterRequest;
import com.cvpilot.botagent.dto.UserResponse;
import com.cvpilot.botagent.service.AuthService;
import com.cvpilot.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Xác thực (Authentication)", description = "APIs đăng ký tài khoản, đăng nhập và quản lý phiên người dùng")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(
            summary = "Đăng ký tài khoản ứng viên mới",
            description = "Tạo mới tài khoản người dùng với email và mật khẩu (được mã hóa bảo mật bằng BCrypt). Người dùng mới sẽ có vai trò mặc định là USER."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "Đăng ký tài khoản thành công, trả về JWT access token",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Dữ liệu gửi lên không hợp lệ hoặc email đã tồn tại trong hệ thống",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            )
    })
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse authResponse = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(authResponse));
    }

    @PostMapping("/login")
    @Operation(
            summary = "Đăng nhập hệ thống",
            description = "Xác thực địa chỉ email và mật khẩu. Nếu hợp lệ và tài khoản đang hoạt động (ACTIVE), hệ thống trả về JWT Bearer token có hiệu lực 24h."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Đăng nhập thành công, trả về access token",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Sai thông tin đăng nhập (email hoặc mật khẩu không chính xác)",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "403",
                    description = "Tài khoản người dùng đã bị khóa (LOCKED)",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            )
    })
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Đăng nhập thành công", authResponse));
    }

    @GetMapping("/me")
    @Operation(
            summary = "Lấy thông tin tài khoản hiện tại",
            description = "Yêu cầu cung cấp JWT Bearer Token tại header Authorization để lấy thông tin hồ sơ của tài khoản đang đăng nhập.",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Lấy thông tin thành công",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Chưa xác thực hoặc token không hợp lệ",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            )
    })
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser(@AuthenticationPrincipal String email) {
        UserResponse userResponse = authService.getCurrentUser(email);
        return ResponseEntity.ok(ApiResponse.success(userResponse));
    }
}
