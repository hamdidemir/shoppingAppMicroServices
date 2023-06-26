package com.demir.userservice.services;


import com.demir.userservice.entity.Order;
import com.demir.userservice.entity.User;
import com.demir.userservice.repository.UserOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;



@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
    private final UserOrderRepository userOrderRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userOrderRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userOrderRepository.findByUsername(username);
    }
    public Optional<User> findById(Long id){return userOrderRepository.findById(id);}

    public void deleteUser(String username) {
        Optional<User> optionalUser = userOrderRepository.findByUsername(username);
        User user = optionalUser.orElse(null);
        if (user != null) {
            userOrderRepository.delete(user);
        } else {
            throw new IllegalArgumentException("User not found: " + username);
        }
    }

    public List<User> getAllUsers() {
        return userOrderRepository.findAll();
    }

    public void deleteUserById(Long id) {
        userOrderRepository.deleteById(id);
    }


    public void addOrderToUser(Long userId, Order newOrder) {
        Optional<User> optionalUser = userOrderRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<Order> orders = user.getOrders();
//            orders.add(newOrder); //Duplicates the order
            user.setOrders(orders);
            userOrderRepository.save(user);
        } else {
            // Handle case when user is not found
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
    }

    public class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String userId) {
            super("User not found with ID: " + userId);
        }
    }


}
