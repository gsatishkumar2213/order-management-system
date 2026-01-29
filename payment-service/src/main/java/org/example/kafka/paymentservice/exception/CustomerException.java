package org.example.kafka.paymentservice.exception;

public class CustomerException extends RuntimeException {
    private String message;

    public CustomerException(String message) {
        this.message = message;
    }
}
