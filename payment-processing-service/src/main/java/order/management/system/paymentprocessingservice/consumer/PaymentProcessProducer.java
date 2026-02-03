package order.management.system.paymentprocessingservice.consumer;

import order.management.system.paymentprocessingservice.entity.Payment;
import order.management.system.paymentprocessingservice.events.PaymentProcessedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentProcessProducer {

    Logger log = LoggerFactory.getLogger(PaymentProcessProducer.class);
    private KafkaTemplate<String, PaymentProcessedEvent> kafkaTemplate;

    public void producePaymentProcessEvent(Payment payment) {
        log.info("PaymentProcessProducer is producing the message :" + payment.getOrderId());
        PaymentProcessedEvent result = new PaymentProcessedEvent(payment.getOrderId(),
                payment.getId(), payment.getStatus(), payment.getAmount());
        kafkaTemplate.send("payment-processing", payment.getOrderId().toString(), result);
        log.info("PaymentProcessProducer has been successfully produced the message :" + payment.getOrderId() + "to the topic: " + "payment-processing");
    }
}
