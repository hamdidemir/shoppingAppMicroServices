package com.demir.userservice.repository;

import com.demir.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserOrderRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long userId);
    Optional<User> findByUsername(String username);
}