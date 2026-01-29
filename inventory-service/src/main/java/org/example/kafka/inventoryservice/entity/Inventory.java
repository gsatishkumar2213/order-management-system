package org.example.kafka.inventoryservice.entity;

public record Inventory(String inventoryId, String orderId, String itemId, int quantityOrdered,
                       int quantityAvailable,
                       String status) {
}
