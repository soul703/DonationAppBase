package org.example.assignment01.mapper;

import org.example.assignment01.dto.reponse.UserResponse;
import org.example.assignment01.entity.Role;
import org.example.assignment01.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUserName(user.getUserName());
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setAddress(user.getAddress());
        response.setNote(user.getNote());
        UserResponse.RoleResponse roleDto = (user.getRole() != null)
                ? mapRoleEntityToDto(user.getRole()) // <-- Gọi một phương thức helper để map
                : null;

        response.setRole(roleDto);
        response.setCreatedAt(user.getCreatedAt()); // ánh xạ createdAt → created
        response.setStatus(user.getStatus());


        return response;
    }
    private UserResponse.RoleResponse mapRoleEntityToDto(Role role) {
        if (role == null) {
            return null;
        }
        UserResponse.RoleResponse roleDto = new UserResponse.RoleResponse();
        roleDto.setId(role.getId());
        roleDto.setRoleName(role.getRoleName());
        return roleDto;
    }
}
