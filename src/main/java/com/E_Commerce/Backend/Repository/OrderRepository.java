package com.E_Commerce.Backend.Repository;

import com.E_Commerce.Backend.Entities.Order;
import com.E_Commerce.Backend.Entities.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUser(Users user);
    Optional<Order> findByTransactionId(String transactionId);
}
