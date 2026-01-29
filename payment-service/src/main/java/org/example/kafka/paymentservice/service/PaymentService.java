package org.example.kafka.paymentservice.service;

import jakarta.transaction.Transactional;
import org.example.kafka.events.PaymentProcessedEvent;
import org.example.kafka.paymentservice.cosntants.PaymentStatus;
import org.example.kafka.paymentservice.dto.PaymentRequestDTO;
import org.example.kafka.paymentservice.entity.Payment;
import org.example.kafka.paymentservice.exception.CustomerException;
import org.example.kafka.paymentservice.kafka.PaymentEventProducer;
import org.example.kafka.paymentservice.repo.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {
    private PaymentRepository paymentRepository;
    private PaymentEventProducer paymentEventProducer;
    private Logger logger = LoggerFactory.getLogger(PaymentService.class);


    public PaymentService(PaymentRepository paymentRepository,
                          PaymentEventProducer paymentEventProducer) {
        this.paymentRepository = paymentRepository;
        this.paymentEventProducer = paymentEventProducer;
    }

    //processPayment(String orderId, double amount) — Main business logic
    @Transactional
    public Payment processPayment(PaymentRequestDTO paymentRequestDTO) {
        logger.info("=== PROCESS PAYMENT START ===");
        Payment payment = new Payment(null,
                paymentRequestDTO.orderId(), paymentRequestDTO.amount(),
                PaymentStatus.PENDING, LocalDateTime.now());
        logger.info("Payment created with ID: {}", payment.getPaymentId());
        logger.info("About to save payment...");
        Payment paymentResponse = paymentRepository.save(payment);
        logger.info("Payment saved successfully with ID: {}", paymentResponse.getPaymentId());
        paymentResponse.setStatus(PaymentStatus.SUCCESS);
        paymentResponse = paymentRepository.save(paymentResponse);
        paymentEventProducer.publishPaymentProcessed(new PaymentProcessedEvent(paymentResponse.getOrderId(),
                paymentResponse.getPaymentId(), paymentResponse.getStatus(),
                paymentResponse.getAmount()));
        return paymentResponse;
    }

    //getPaymentById(String paymentId) — Retrieve payment
    public Payment getPaymentById(String paymentId) {
        return paymentRepository.findById(paymentId).orElseThrow(() -> new CustomerException(
                "Nothing found"));
    }

    //getPaymentByOrderId(String orderId) — Retrieve by order
    public Payment getPaymentByOrderId(String orderId) {
        return paymentRepository.findByOrderId(orderId).orElseThrow(() -> new CustomerException(
                "No order found"));
    }

    //getPaymentsByStatus(String status) — Get all by status
    public List<Payment> getPaymentsByStatus(String status) {
        return paymentRepository.findByStatus(status);
    }

    //updatePaymentStatus(String paymentId, String status) — Update status
    public Payment updatePaymentStatus(String paymentId, String status) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() ->
                new CustomerException("No Payment found"));
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }
}
