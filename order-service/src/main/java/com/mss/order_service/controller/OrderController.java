package com.mss.order_service.controller;

import com.mss.order_service.dto.OrderRequest;
import com.mss.order_service.dto.OrderResponse;
import com.mss.order_service.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
    private OrderService orderService;

    @PostMapping("/save")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Object> updateOrderStatus(@PathVariable Long id, @RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, request.getOrder().getStatus()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.deleteOrder(id));
    }
}
