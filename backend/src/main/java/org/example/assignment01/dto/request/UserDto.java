package org.example.assignment01.dto.request;
import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.example.assignment01.entity.Role;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    @NotEmpty(message = "Username không được để trống")
    private String userName;
    @NotEmpty(message = "Password không được để trống")
    private String password;
    @NotEmpty(message = "Họ tên không được để trống")
    private String fullName;
    @NotEmpty(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;
//    @NotNull(message = "Role ID không được để trống")
//    private String role;
}