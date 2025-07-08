package org.example.assignment01.repository;

import org.example.assignment01.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> , JpaSpecificationExecutor<User> {
    Optional<User> findByUserName(String username);
    boolean existsByUserName(String username);
    boolean existsByEmail(String email);
    // This interface extends JpaRepository, which provides CRUD operations for the User entity.
    // Additional custom query methods can be defined here if needed.

    // Example of a custom query method to find a user by username

}
