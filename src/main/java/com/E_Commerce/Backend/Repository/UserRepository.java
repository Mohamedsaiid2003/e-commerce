package com.E_Commerce.Backend.Repository;

import com.E_Commerce.Backend.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    @Query("SELECT u FROM users u WHERE u.email = ?1")
     Optional<Users> findByEmail(String email);

    @Query("SELECT u FROM users u WHERE u.email = ?1")
    Users findByUsername(String email);

}
