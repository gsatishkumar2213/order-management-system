package org.example.kafka.orderservice.exception;

public class CustomException extends RuntimeException{
    String message;

    public CustomException(String message) {
        this.message = message;
    }
}
