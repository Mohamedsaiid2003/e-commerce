package com.E_Commerce.Backend.DTO.response;

import com.E_Commerce.Backend.Entities.Order;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;





public class OrderResponse {
    private Long orderId;
    private BigDecimal totalPrice;
    private String status;
    private LocalDateTime createdAt;

    public OrderResponse(Order order) {
        this.orderId = order.getId();
        this.totalPrice = order.getTotalPrice();
        this.status = order.getStatus().name();
        this.createdAt = order.getCreatedAt();
    }
}
