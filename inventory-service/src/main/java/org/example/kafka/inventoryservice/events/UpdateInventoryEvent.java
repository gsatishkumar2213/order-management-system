package org.example.kafka.inventoryservice.events;

public record UpdateInventoryEvent(
        String orderId,
        String itemId,      // which item to update
        int quantity,       // how much to reduce
        String status
) {}