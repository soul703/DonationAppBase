package org.example.assignment01.service;

import org.example.assignment01.dto.reponse.UserResponse;
import org.example.assignment01.dto.request.UserCreateRequest;
import org.example.assignment01.dto.request.UserDto;
import org.example.assignment01.dto.request.UserUpdateRequest;
import org.example.assignment01.entity.Role;
import org.example.assignment01.entity.User;
import org.example.assignment01.exception.ResourceNotFoundException;
import org.example.assignment01.mapper.UserMapper;
import org.example.assignment01.repository.RoleRepository;
import org.example.assignment01.repository.UserRepository;


import org.example.assignment01.repository.UserSpecification;
import org.example.assignment01.useenum.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private UserMapper userMapper;

    public UserResponse createUser(UserCreateRequest request) {
        if (userRepository.existsByUserName(request.getUserName())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + request.getRoleId()));

        User user = new User();
        user.setUserName(request.getUserName());
        user.setPassword(request.getPassword()); // Cần mã hóa trong thực tế
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());
        user.setRole(role);
        user.setStatus(UserStatus.ACTIVE);

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    public Page<UserResponse> getAllUsers(String email, String phoneNumber, Pageable pageable) {
        Specification<User> spec = UserSpecification.filterBy(email, phoneNumber);
        return userRepository.findAll(spec, pageable).map(userMapper::toResponse);
    }

    public UserResponse getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.toResponse(user);
    }

    public UserResponse updateUser(Integer id, UserUpdateRequest request) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (request.getFullName() != null) existingUser.setFullName(request.getFullName());
        if (request.getEmail() != null) existingUser.setEmail(request.getEmail());
        if (request.getPhoneNumber() != null) existingUser.setPhoneNumber(request.getPhoneNumber());
        if (request.getAddress() != null) existingUser.setAddress(request.getAddress());
        if (request.getNote() != null) existingUser.setNote(request.getNote());
        if (request.getStatus() != null) existingUser.setStatus(request.getStatus());

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toResponse(updatedUser);
    }

    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}