package org.example.kafka.shipmentservice.entity;

public record Shipment(String shipmentId, String orderId, String deliveryAddress, String status,
                       String trackingNumber) {
}
