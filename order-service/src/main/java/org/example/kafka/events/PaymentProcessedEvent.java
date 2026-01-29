package org.example.kafka.events;

public record PaymentProcessedEvent(
        Long orderId,
        String paymentId,
        double amount,
        String status) {
}

