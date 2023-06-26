package com.demir.userservice.controller;


import com.demir.userservice.entity.Order;
import com.demir.userservice.entity.User;
import com.demir.userservice.repository.UserOrderRepository;
import com.demir.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserOrderRepository userOrderRepository;
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    @DeleteMapping("/by-username/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<Optional<User>> getUserByID(@PathVariable Long id){
        Optional<User> user=userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/by-id/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<?> addOrderToUser(@PathVariable Long userId, @RequestBody Order newOrder) {
        try {
            userService.addOrderToUser(userId, newOrder);
            return ResponseEntity.ok("Order added to the user successfully.");
        } catch (UserService.UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding the order to the user.");
        }
    }
}
