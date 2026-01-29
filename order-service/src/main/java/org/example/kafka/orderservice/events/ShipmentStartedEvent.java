package org.example.kafka.orderservice.events;

public record ShipmentStartedEvent(String orderId, String customerId, String trackingNumber, String status) {}
