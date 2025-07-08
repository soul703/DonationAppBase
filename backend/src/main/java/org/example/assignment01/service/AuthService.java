package org.example.assignment01.service;

import org.example.assignment01.dto.reponse.UserResponse;
import org.example.assignment01.dto.request.LoginRequest;
import org.example.assignment01.entity.User;
import org.example.assignment01.exception.ResourceNotFoundException;
import org.example.assignment01.mapper.UserMapper;
import org.example.assignment01.repository.UserRepository;
import org.example.assignment01.useenum.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    // Nếu bạn đã mã hóa mật khẩu, hãy inject PasswordEncoder ở đây
    // @Autowired private PasswordEncoder passwordEncoder;

    public UserResponse login(LoginRequest request) {
        // 1. Tìm user bằng username
        User user = userRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid username or password"));

        // 2. Kiểm tra mật khẩu (so sánh chuỗi trực tiếp - KHÔNG AN TOÀN)
        // Trong thực tế, bạn sẽ dùng: passwordEncoder.matches(request.getPassword(), user.getPassword())
        if (!user.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        // 3. Kiểm tra trạng thái tài khoản
        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new IllegalStateException("User account is not active. Status: " + user.getStatus());
        }

        // 4. Nếu mọi thứ ổn, trả về thông tin user
        return userMapper.toResponse(user);
    }
}