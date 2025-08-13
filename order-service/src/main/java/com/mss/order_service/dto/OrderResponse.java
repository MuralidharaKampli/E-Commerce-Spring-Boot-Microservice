package com.mss.order_service.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class OrderResponse {

    private String message;
    private OrderDetails order;

    @Data
    public static class OrderDetails {
        private Long orderId;
        private Long userId;
        private LocalDateTime orderDate;
        private String status;
        private List<OrderItemDTO> items;
        private double totalAmount;
        private Address address;
        private Long txnId;
    }
}
