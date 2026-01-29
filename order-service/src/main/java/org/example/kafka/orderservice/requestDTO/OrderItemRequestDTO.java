package org.example.kafka.orderservice.requestDTO;

public record OrderItemRequestDTO (
     String itemName,
     int quantity){
}