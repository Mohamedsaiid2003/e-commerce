package com.E_Commerce.Backend.Service;

import com.E_Commerce.Backend.Entities.Category;
import com.E_Commerce.Backend.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;


    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    // Get By Admin
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Get By User
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public Category updateCategory(Long id, Category updatedCategory) {
        Category category = getCategoryById(id);
        category.setName(updatedCategory.getName());
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
    }
}
