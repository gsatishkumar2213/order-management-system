package org.example.kafka.orderservice.responseDTO;

public record OrderItemResponseDTO(Long itemId,
                  String itemName,
int quantity) {

}
