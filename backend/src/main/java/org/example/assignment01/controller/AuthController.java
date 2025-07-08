package org.example.assignment01.controller;

import jakarta.validation.Valid;
import org.example.assignment01.dto.reponse.ApiResponse;
import org.example.assignment01.dto.reponse.UserResponse;
import org.example.assignment01.dto.request.LoginRequest;
import org.example.assignment01.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponse>> login(@Valid @RequestBody LoginRequest request) {
        UserResponse userResponse = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success(userResponse, "Login successful."));
    }

    // Sau này bạn có thể thêm API đăng ký ở đây, gọi đến userService.createUser()
    // @PostMapping("/register")
    // public ... register(...) { ... }
}