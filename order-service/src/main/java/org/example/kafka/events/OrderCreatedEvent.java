package org.example.kafka.events;

public record OrderCreatedEvent(Long orderId, String customerId, double amount) {
}
