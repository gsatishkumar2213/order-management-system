package org.example.kafka.orderservice.events;

public record PaymentFailedEvent(String orderId, String reason) {}
