package com.demir.orderservice.service;

import com.demir.orderservice.entity.Order;
import com.demir.orderservice.event.OrderPlacedEvent;
import com.demir.orderservice.repository.UserOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final UserOrderRepository userOrderRepository;

    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate;

    public void placeOrder(Order order) {
        // Save the new order
        Order savedOrder = userOrderRepository.save(order);

        // Update the user service via WebClient
        webClientBuilder.build().post()
                .uri("http://user-service/users/{userId}/orders", order.getUser().getId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(savedOrder)
                .retrieve()
                .bodyToMono(Void.class)
                .block(); // Blocking for simplicity

        kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getId(), order.getUser().getId()));
    }

    public List<Order> getAllOrders() {
        return userOrderRepository.findAll();
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return userOrderRepository.findByUserId(userId);
    }



}
