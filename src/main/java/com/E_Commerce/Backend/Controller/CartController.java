package com.E_Commerce.Backend.Controller;


import com.E_Commerce.Backend.Entities.Cart;
import com.E_Commerce.Backend.Service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable Long userId) {
        return cartService.getUserCart(userId);
    }

    @PostMapping("/add")
    public Cart addProductToCart(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam Integer quantity
    ) {
        return cartService.addProductToCart(userId, productId, quantity);
    }
    @DeleteMapping("/clear/{userId}")
    public void clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
    }

}
