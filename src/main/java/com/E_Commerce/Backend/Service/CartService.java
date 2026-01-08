package com.E_Commerce.Backend.Service;


import com.E_Commerce.Backend.Entities.Cart;
import com.E_Commerce.Backend.Entities.CartItem;
import com.E_Commerce.Backend.Entities.Product;
import com.E_Commerce.Backend.Entities.Users;
import com.E_Commerce.Backend.Repository.CartRepository;
import com.E_Commerce.Backend.Repository.ProductRepository;
import com.E_Commerce.Backend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private  final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Cart getUserCart(Long userId) {

        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Users user = userRepository.findById(userId)
                            .orElseThrow(() -> new IllegalStateException("User not found"));

                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }

    public Cart addProductToCart(Long userId, Long productId, int quantity) {

        Cart cart = getUserCart(userId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalStateException("Product not found"));

        CartItem existingItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItem item = new CartItem();
            item.setProduct(product);
            item.setQuantity(quantity);
            item.setCart(cart);
            cart.getCartItems().add(item);
        }

        return cartRepository.save(cart);
    }



    public void clearCart(Long userId) {
        Cart cart = getUserCart(userId);
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

}
