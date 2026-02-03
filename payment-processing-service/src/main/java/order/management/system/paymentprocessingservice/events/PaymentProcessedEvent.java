package order.management.system.paymentprocessingservice.events;

public record PaymentProcessedEvent(
        Long orderId,
        Long paymentId,
        String status,
        Double amount
) {
}