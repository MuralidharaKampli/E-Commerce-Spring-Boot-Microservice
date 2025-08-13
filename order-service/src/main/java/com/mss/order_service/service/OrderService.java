package com.mss.order_service.service;

import com.mss.order_service.dto.Message;
import com.mss.order_service.dto.OrderRequest;
import com.mss.order_service.dto.OrderResponse;
import com.mss.order_service.entity.OrderStatus;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request);

    OrderResponse getOrderById(Long id);

    Message updateOrderStatus(Long id, OrderStatus status);

    Message deleteOrder(Long id);
}
