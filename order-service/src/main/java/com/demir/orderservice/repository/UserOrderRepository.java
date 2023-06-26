package com.demir.orderservice.repository;

import com.demir.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserOrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}