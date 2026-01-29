package org.example.kafka.orderservice.kafka;


import org.example.kafka.events.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventProducer {
    Logger logger = LoggerFactory.getLogger(OrderEventProducer.class);
    private KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public OrderEventProducer(KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishOrderCreated(OrderCreatedEvent orderCreatedEvent) {
        logger.info("Publishing OrderCreatedEvent for orderId: {}", orderCreatedEvent.orderId());
        kafkaTemplate.send("order-events", orderCreatedEvent.orderId().toString(),
                orderCreatedEvent);
        logger.info("OrderCreatedEvent published successfully");
    }
}
