package org.example.kafka.shipmentservice.entity;

public record StartShipmentEvent(
        String orderId,
        String customerId,
        String deliveryAddress,
        String status
) {}