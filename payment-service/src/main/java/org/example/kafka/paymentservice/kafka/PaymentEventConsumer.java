package org.example.kafka.paymentservice.kafka;

import org.example.kafka.events.OrderCreatedEvent;
import org.example.kafka.events.PaymentProcessedEvent;
import org.example.kafka.paymentservice.dto.PaymentRequestDTO;
import org.example.kafka.paymentservice.entity.Payment;
import org.example.kafka.paymentservice.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventConsumer {
    private PaymentService paymentService;
    private PaymentEventProducer paymentEventProducer;
    private Logger logger = LoggerFactory.getLogger(PaymentEventConsumer.class);

    public PaymentEventConsumer(PaymentService paymentService,
                                PaymentEventProducer paymentEventProducer) {
        this.paymentService = paymentService;
        this.paymentEventProducer = paymentEventProducer;
    }

    @KafkaListener(topics = "order-events", groupId = "payment-service-group")
    public void consumeOrderEvent(OrderCreatedEvent orderCreatedEvent) {
        System.out.println("===== LISTENER CALLED =====");
        logger.info("Consumed OrderCreatedEvent: {}", orderCreatedEvent);
        PaymentRequestDTO paymentRequestDTO =
                new PaymentRequestDTO(orderCreatedEvent.orderId(),
                        orderCreatedEvent.amount());
        Payment payment = paymentService.processPayment(paymentRequestDTO);
        PaymentProcessedEvent paymentProcessedEvent =
                new PaymentProcessedEvent(payment.getOrderId(),
                        payment.getPaymentId(), payment.getStatus(), payment.getAmount());
        paymentEventProducer.publishPaymentProcessed(paymentProcessedEvent);
    }
}
