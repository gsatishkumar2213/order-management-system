package order.management.system.paymentprocessingservice.consumer;

import order.management.system.paymentprocessingservice.entity.Payment;
import order.management.system.paymentprocessingservice.events.OrderCreatedEvent;
import order.management.system.paymentprocessingservice.service.PaymentProcessingServic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PaymentConsumer {
    private Logger log = LoggerFactory.getLogger(PaymentConsumer.class);
    private PaymentProcessingServic paymentProcessingService;

    public PaymentConsumer(PaymentProcessingServic paymentProcessingService) {
        this.paymentProcessingService = paymentProcessingService;
    }

    @KafkaListener(
            topics = "orders",
            groupId = "payment-group"

    )
    public void consumePayment(OrderCreatedEvent orderCreatedEvent,
                               Acknowledgment ack) {
        log.info("Received new payment message: " + orderCreatedEvent.orderId());
        Payment payment = new Payment(null,
                orderCreatedEvent.orderId(),
                orderCreatedEvent.customerId(),
                orderCreatedEvent.amount(),
                "PENDING",
                LocalDateTime.now());
        try {
            Payment result = paymentProcessingService.checkIfPaymentPresent(payment);
            ack.acknowledge();
        } catch (Exception e) {
            log.error("Failed to process payment", e);
        }
    }
}
