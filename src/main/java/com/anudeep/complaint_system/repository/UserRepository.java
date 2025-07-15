package com.anudeep.complaint_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anudeep.complaint_system.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
