package org.example.kafka.orderservice.controller;

import org.example.kafka.orderservice.constants.OrderStatus;
import org.example.kafka.orderservice.entity.Order;
import org.example.kafka.orderservice.entity.OrderItem;
import org.example.kafka.orderservice.requestDTO.OrderItemRequestDTO;
import org.example.kafka.orderservice.requestDTO.OrderRequestDTO;
import org.example.kafka.orderservice.responseDTO.OrderItemResponseDTO;
import org.example.kafka.orderservice.responseDTO.ResponseDTO;
import org.example.kafka.orderservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private OrderService orderService;
    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/")
    public ResponseEntity<ResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        logger.info("Order Request: {}",orderRequestDTO );
        Order orderResponse =orderService.createOrder(orderRequestDTO);
        logger.info("Returned order items size: {}", orderResponse.getOrderItems().size());
        List<OrderItemResponseDTO> orderItemResponseDTOList = orderResponse.getOrderItems().stream()
                .map(this::convertToResponseDTO).toList();

        ResponseDTO responseDTO = new ResponseDTO(orderResponse.getOrderId(),orderResponse.getOrderStatus(),
                orderResponse.getAmount(),orderResponse.getCustomerAddress(),orderItemResponseDTOList);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ResponseDTO> getOrderById(@PathVariable Long orderId) {
        Order order =orderService.getOrderById(orderId);
        List<OrderItemResponseDTO> orderItemResponseDTOList =
                order.getOrderItems().stream()
                     .map(this::convertToResponseDTO)
                     .toList();
        ResponseDTO responseDTO = new ResponseDTO(order.getOrderId(),order.getOrderStatus(),
                order.getAmount(),
                order.getCustomerAddress(),orderItemResponseDTOList);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ResponseDTO>> getOrderByCustomerId(@PathVariable String customerId){
        List<Order> orders = orderService.getOrderByCustomerId(customerId);
        List<ResponseDTO> responseDTOS =
                orders.stream().map(order -> new ResponseDTO(order.getOrderId(),order.getOrderStatus()
                ,order.getAmount(),order.getCustomerAddress(),
                        order.getOrderItems().stream()
                                .map(this::convertToResponseDTO).toList())).toList();

        return ResponseEntity.ok(responseDTOS);
    }

    private OrderItemResponseDTO convertToResponseDTO(OrderItem item) {
        return new OrderItemResponseDTO(item.getItemId(), item.getItemName(), item.getQuantity());
    }
}
