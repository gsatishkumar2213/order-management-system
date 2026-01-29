package org.example.kafka.paymentservice.events;

public record PaymentFailedEvent(
        String orderId,
        String reason,       // WHY it failed (insufficient funds, etc.)
        String status        // "FAILED"
) {}