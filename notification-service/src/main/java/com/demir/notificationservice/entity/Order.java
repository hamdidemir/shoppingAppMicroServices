package com.demir.notificationservice.entity;


import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long id;
    private String description;
    private String orderNumber;
    private User user;
}