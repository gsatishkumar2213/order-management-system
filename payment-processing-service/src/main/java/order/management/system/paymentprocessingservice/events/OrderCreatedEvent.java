package order.management.system.paymentprocessingservice.events;

public record OrderCreatedEvent(Long orderId, String customerId, double amount) {
}

