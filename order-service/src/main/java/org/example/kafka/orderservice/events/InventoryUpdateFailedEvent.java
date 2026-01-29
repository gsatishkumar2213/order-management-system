package org.example.kafka.orderservice.events;

public record InventoryUpdateFailedEvent(String orderId, String itemId, String reason) {}

