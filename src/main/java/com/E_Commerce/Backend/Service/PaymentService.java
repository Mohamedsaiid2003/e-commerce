package com.E_Commerce.Backend.Service;


import com.E_Commerce.Backend.Entities.Order;
import com.E_Commerce.Backend.Enum.OrderStatus;
import com.E_Commerce.Backend.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final OrderRepository orderRepository;

    public String initiatePayment(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("Order not found"));
        return "https://paymob.com/payment-url";
    }

    public void handleCallback(String transactionId, boolean success) {
        Order order = orderRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException("Order not found with transaction ID: " + transactionId));

        order.setStatus(success ? OrderStatus.PAID : OrderStatus.FAILED);
        orderRepository.save(order);
    }

}
