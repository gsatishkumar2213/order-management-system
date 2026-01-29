package org.example.kafka.orderservice.entity;

import jakarta.persistence.*;

@Entity
public class OrderItem{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long itemId;
        String itemName;
        int quantity;
        @ManyToOne
        Order order;

        public OrderItem() {
        }

        public OrderItem(Long itemId, String itemName, int quantity, Order order) {
                this.itemId = itemId;
                this.itemName = itemName;
                this.quantity = quantity;
                this.order = order;
        }

        public Long getItemId() {
                return itemId;
        }

        public void setItemId(Long itemId) {
                this.itemId = itemId;
        }

        public String getItemName() {
                return itemName;
        }

        public void setItemName(String itemName) {
                this.itemName = itemName;
        }

        public int getQuantity() {
                return quantity;
        }

        public void setQuantity(int quantity) {
                this.quantity = quantity;
        }

        public Order getOrder() {
                return order;
        }

        public void setOrder(Order order) {
                this.order = order;
        }
}
