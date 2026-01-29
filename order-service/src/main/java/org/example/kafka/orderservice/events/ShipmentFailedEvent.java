package org.example.kafka.orderservice.events;

public record ShipmentFailedEvent(String orderId, String reason) {}