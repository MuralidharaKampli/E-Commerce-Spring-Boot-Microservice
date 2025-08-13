package com.mss.order_service.dto;

import com.mss.order_service.entity.OrderStatus;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private OrderDTO order;

    @Data
    public static class OrderDTO {
        private Long userId;
        private List<OrderItemDTO> items;
        private OrderStatus status;
        private Address address;
    }
}
