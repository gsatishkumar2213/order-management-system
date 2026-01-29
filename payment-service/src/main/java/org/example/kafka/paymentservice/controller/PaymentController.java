package org.example.kafka.paymentservice.controller;

import org.example.kafka.paymentservice.dto.PaymentRequestDTO;
import org.example.kafka.paymentservice.dto.PaymentResponseDTO;
import org.example.kafka.paymentservice.entity.Payment;
import org.example.kafka.paymentservice.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private PaymentService paymentService;
    private Logger logger = LoggerFactory.getLogger(PaymentController.class);

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/process")
    public ResponseEntity<PaymentResponseDTO> paymentProcess(
            @RequestBody PaymentRequestDTO paymentRequestDTO) {
        Payment payment = paymentService.processPayment(paymentRequestDTO);
        PaymentResponseDTO paymentResponseDTO = getPaymentResponseDTO(payment);
        return ResponseEntity.ok(paymentResponseDTO);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponseDTO> findByPaymentId(@PathVariable String paymentId) {
        Payment payment = paymentService.getPaymentById(paymentId);
        PaymentResponseDTO paymentResponseDTO = getPaymentResponseDTO(payment);
        return ResponseEntity.ok(paymentResponseDTO);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponseDTO> findByOrderId(@PathVariable String orderId) {
        Payment payment = paymentService.getPaymentByOrderId(orderId);
        PaymentResponseDTO paymentResponseDTO = getPaymentResponseDTO(payment);
        return ResponseEntity.ok(paymentResponseDTO);
    }


    @GetMapping("/status/{status}")
    public ResponseEntity<List<PaymentResponseDTO>> findByStatus(@PathVariable String status) {
        List<Payment> payments = paymentService.getPaymentsByStatus(status);
        List<PaymentResponseDTO> paymentResponseDTOS =
                payments.stream()
                        .map(this::getPaymentResponseDTO).toList();
        return ResponseEntity.ok(paymentResponseDTOS);
    }

    private PaymentResponseDTO getPaymentResponseDTO(Payment payment) {
        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO(payment.getPaymentId()
                , payment.getOrderId(), payment.getAmount(), payment.getStatus());
        return paymentResponseDTO;
    }
}
