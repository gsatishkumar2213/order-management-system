package org.example.kafka;

public record Order(String orderId,
                    String customerId,
                    String productId,
                    String productNameAtPurchase,
                    double priceAtPurchase,
                    int quantity) {
}
