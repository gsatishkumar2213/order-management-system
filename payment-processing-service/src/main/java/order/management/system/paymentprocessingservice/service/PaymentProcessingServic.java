package order.management.system.paymentprocessingservice.service;


import order.management.system.paymentprocessingservice.entity.Payment;
import order.management.system.paymentprocessingservice.exception.InvalidinputException;
import order.management.system.paymentprocessingservice.repo.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PaymentProcessingServic {
    private Logger log = LoggerFactory.getLogger(PaymentProcessingServic.class);
    private PaymentRepository paymentRepository;

    public PaymentProcessingServic(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public Payment savePaymentIfnotExist(Payment payment) {
        Optional<Payment> existing = paymentRepository.findByOrderId(payment.getOrderId());
        if (existing.isPresent()) {
            log.info("Payment id: {} already has been processed ", payment.getOrderId());
            return existing.get();
        }
        return paymentRepository.save(payment);
    }

    public Payment checkIfPaymentPresent(Payment payment) {
        log.info("Payment request has been recieved: " + payment.getOrderId());
        if (payment == null) {
            throw new InvalidinputException("Payment can not be null ");
        } else {
            if (payment.getOrderId() == null
                    || payment.getAmount() <= 0
                    || (payment.getCustomerId() == null || payment.getCustomerId().isBlank())) {
                throw new InvalidinputException("Invalid Request found: "
                        + payment.toString()
                );
            }
        }
        return this.savePaymentIfnotExist(payment);
    }
}
