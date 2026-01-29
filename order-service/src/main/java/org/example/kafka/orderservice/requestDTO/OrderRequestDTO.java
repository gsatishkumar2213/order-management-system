package org.example.kafka.orderservice.requestDTO;

import org.example.kafka.orderservice.entity.OrderItem;

import java.util.List;

public record OrderRequestDTO(
        String customerId,
        double amount,
        List<OrderItemRequestDTO> items,
        String address
) {}

