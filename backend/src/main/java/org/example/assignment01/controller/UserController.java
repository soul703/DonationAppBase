package org.example.assignment01.controller;


import jakarta.validation.Valid;
import org.example.assignment01.dto.reponse.ApiResponse;
import org.example.assignment01.dto.reponse.ContributionResponse;
import org.example.assignment01.dto.reponse.UserResponse;
import org.example.assignment01.dto.request.UserCreateRequest;
import org.example.assignment01.dto.request.UserUpdateRequest;
import org.example.assignment01.service.UserDonationService;
import org.example.assignment01.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Controller chịu trách nhiệm xử lý các yêu cầu liên quan đến người dùng (User),
 * bao gồm tạo, cập nhật, xoá, xem danh sách người dùng và truy vấn các lần đóng góp.
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDonationService userDonationService;

    /**
     * API tạo mới người dùng.
     *
     * @param request thông tin người dùng cần tạo
     * @return thông tin người dùng vừa được tạo kèm theo status CREATED
     */
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody UserCreateRequest request) {
        UserResponse createdUser = userService.createUser(request);

        // Tạo URI cho resource vừa tạo (RESTful)
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(ApiResponse.success(createdUser, "User created successfully."));
    }

    /**
     * API lấy danh sách người dùng, có hỗ trợ phân trang.
     *
     * @param pageable thông tin phân trang (page, size, sort)
     * @return trang chứa danh sách người dùng
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<UserResponse>>> getAllUsers(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phoneNumber,
            Pageable pageable) {
        Page<UserResponse> users = userService.getAllUsers(email, phoneNumber, pageable);
        return ResponseEntity.ok(ApiResponse.success(users, "Users fetched successfully."));
    }

    /**
     * API lấy thông tin chi tiết một người dùng theo ID.
     *
     * @param id ID của người dùng
     * @return thông tin người dùng tương ứng
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Integer id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success(user, "User fetched successfully."));
    }

    /**
     * API cập nhật thông tin người dùng.
     *
     * @param id      ID người dùng cần cập nhật
     * @param request thông tin cập nhật
     * @return người dùng sau khi được cập nhật
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable Integer id,
            @Valid @RequestBody UserUpdateRequest request) {

        UserResponse updatedUser = userService.updateUser(id, request);
        return ResponseEntity.ok(ApiResponse.success(updatedUser, "User updated successfully."));
    }

    /**
     * API xoá người dùng theo ID.
     *
     * @param id ID người dùng
     * @return thông báo xoá thành công
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success("User deleted successfully."));
    }

    /**
     * API lấy danh sách các đợt đóng góp của người dùng (1 người có thể đóng góp nhiều dự án).
     *
     * @param userId   ID người dùng
     * @param pageable phân trang kết quả
     * @return danh sách đóng góp của người dùng
     */
    @GetMapping("/{userId}/contributions")
    public ResponseEntity<ApiResponse<Page<ContributionResponse>>> getUserContributions(
            @PathVariable Integer userId,
            Pageable pageable) {

        Page<ContributionResponse> contributions = userDonationService.getContributionsByUserId(userId, pageable);
        return ResponseEntity.ok(ApiResponse.success(contributions, "User contributions fetched successfully."));
    }
}
