package org.example.kafka.paymentservice.dto;

public record PaymentRequestDTO(
        Long orderId,
        double amount
) {
}
