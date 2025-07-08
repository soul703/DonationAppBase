package org.example.assignment01.dto.reponse;

import lombok.Data;
import org.example.assignment01.useenum.UserStatus;

import java.time.LocalDateTime;

/**
 * Lớp `UserResponse` đại diện cho dữ liệu trả về (response) từ API liên quan đến người dùng.
 *
 * Thường được sử dụng trong các API như:
 * - GET /api/v1/users/{id}
 * - POST /api/v1/users
 * - PUT /api/v1/users/{id}
 *
 * Bao gồm thông tin cơ bản của người dùng và vai trò (role) tương ứng.
 */
@Data
public class UserResponse {

    /**
     * ID duy nhất của người dùng.
     */
    private Integer id;

    /**
     * Tên đăng nhập (username), phải là duy nhất.
     */
    private String userName;

    /**
     * Họ và tên đầy đủ của người dùng.
     */
    private String fullName;

    /**
     * Địa chỉ email của người dùng.
     */
    private String email;

    /**
     * Số điện thoại của người dùng.
     */
    private String phoneNumber;

    /**
     * Địa chỉ liên hệ.
     */
    private String address;

    /**
     * Ghi chú thêm (nếu có).
     */
    private String note;

    /**
     * Trạng thái của người dùng (ví dụ: ACTIVE, INACTIVE, LOCKED...).
     */
    private UserStatus status;

    /**
     * Ngày giờ tài khoản được tạo.
     */
    private LocalDateTime createdAt;

    /**
     * Vai trò của người dùng (admin, user...).
     * Trả về một đối tượng `RoleResponse` thay vì chỉ ID.
     */
    private RoleResponse role;

    /**
     * Lớp lồng để chứa thông tin của Role.
     * Chỉ trả về những trường cần thiết để tránh lộ thông tin không cần thiết.
     */
    @Data
    public static class RoleResponse {
        /**
         * ID của vai trò (role).
         */
        private Integer id;

        /**
         * Tên vai trò (ví dụ: ADMIN, USER...).
         */
        private String roleName;
    }
}
