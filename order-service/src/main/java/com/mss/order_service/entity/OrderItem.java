package com.mss.order_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "M_MS_ORDER_ITEM")
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private int quantity;

    private double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
}
