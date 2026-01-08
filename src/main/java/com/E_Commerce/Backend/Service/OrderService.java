package com.E_Commerce.Backend.Service;

import com.E_Commerce.Backend.Entities.*;
import com.E_Commerce.Backend.Enum.OrderStatus;
import com.E_Commerce.Backend.Repository.CartRepository;
import com.E_Commerce.Backend.Repository.OrderRepository;
import com.E_Commerce.Backend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public Order placeOrder(Long userId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("Cart not found"));

        Order order = new Order();
        order.setUser(cart.getUser());
        order.setStatus(OrderStatus.PENDING);

        BigDecimal totalPrice = BigDecimal.ZERO;

        for (CartItem item : cart.getCartItems()) {

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getProduct().getPrice());

            BigDecimal itemTotal =
                    item.getProduct().getPrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity()));

            totalPrice = totalPrice.add(itemTotal);

            order.getOrderItems().add(orderItem);
        }

        order.setTotalPrice(totalPrice);

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByEmail(String email) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        return orderRepository.findByUser(user);
    }
}
