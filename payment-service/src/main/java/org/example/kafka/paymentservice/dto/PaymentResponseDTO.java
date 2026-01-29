package org.example.kafka.paymentservice.dto;

public record PaymentResponseDTO(
        Long paymentId,
        Long orderId,
        double amount,
        String status
) {
}
