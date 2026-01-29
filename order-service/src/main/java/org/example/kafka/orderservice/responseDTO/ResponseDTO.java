package org.example.kafka.orderservice.responseDTO;

import org.example.kafka.orderservice.entity.OrderItem;

import java.util.List;

public record ResponseDTO(Long orderId, String Status, double Amount, String Address,
                          List<OrderItemResponseDTO> items) {
}
