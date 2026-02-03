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
    private PaymentProcessProducer paymentProcessProducer;


    public PaymentConsumer(PaymentProcessingServic paymentProcessingService,
                           PaymentProcessProducer paymentProcessProducer) {
        this.paymentProcessingService = paymentProcessingService;
        this.paymentProcessProducer = paymentProcessProducer;
    }

    @KafkaListener(
            topics = "order-created",
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
        Payment result = paymentProcessingService.checkIfPaymentPresent(payment);
        log.info("Payment processed with status: " + result.getStatus());
        paymentProcessProducer.producePaymentProcessEvent(payment);
        ack.acknowledge();
    }
}
