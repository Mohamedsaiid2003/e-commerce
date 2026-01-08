package com.E_Commerce.Backend.Repository;

import com.E_Commerce.Backend.Entities.Cart;
import com.E_Commerce.Backend.Entities.Order;
import com.E_Commerce.Backend.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findByUserId(Long userId);
}
