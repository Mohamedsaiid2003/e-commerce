package com.E_Commerce.Backend.Controller;


import com.E_Commerce.Backend.DTO.response.OrderResponse;
import com.E_Commerce.Backend.Entities.Order;
import com.E_Commerce.Backend.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;


    @PostMapping("/place/{userId}")
    public Order placeOrder(@PathVariable Long userId) {
        return orderService.placeOrder(userId);
    }

    //Show All Orders From User
    @GetMapping("/allOrderfromUser/")
    public List<Order> getOrdersByEmail(@RequestParam String email) {
        return orderService.getOrdersByEmail(email);
    }

    //Show All Orders From Admin
    @GetMapping("/allOrderfromAdmin/")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

}
