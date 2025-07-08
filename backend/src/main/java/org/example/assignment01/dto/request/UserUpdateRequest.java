package org.example.assignment01.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Data;
import org.example.assignment01.useenum.UserStatus;

// Các trường trong DTO này đều là tùy chọn khi cập nhật
@Data
public class UserUpdateRequest {
    private String fullName;

    @Email(message = "Invalid email format")
    private String email;

    private String phoneNumber;
    private String address;
    private String note;
    private UserStatus status; // Cho phép admin cập nhật trạng thái user
}
