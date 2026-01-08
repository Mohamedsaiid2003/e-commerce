package com.E_Commerce.Backend.Repository;

import com.E_Commerce.Backend.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
