package org.example.kafka.orderservice.events;

public record InventoryUpdatedEvent(String orderId, String itemId, int quantity, String status) {}

