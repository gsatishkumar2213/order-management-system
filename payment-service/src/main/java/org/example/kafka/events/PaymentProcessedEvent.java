package org.example.kafka.events;

public record PaymentProcessedEvent(
        Long orderId,
        Long paymentId,
        String status,
        double amount
) {
}