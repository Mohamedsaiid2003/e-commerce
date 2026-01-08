package com.E_Commerce.Backend.Service;

import com.E_Commerce.Backend.DTO.request.ProductRequest;
import com.E_Commerce.Backend.Entities.Category;
import com.E_Commerce.Backend.Entities.Product;
import com.E_Commerce.Backend.Repository.CategoryRepository;
import com.E_Commerce.Backend.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product addProduct(ProductRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalStateException("Category not found"));

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getStock());
        product.setCategory(category);
        return productRepository.save(product);
    }


    public Product updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(updatedProduct.getName());
                    product.setPrice(updatedProduct.getPrice());
                    product.setDescription(updatedProduct.getDescription());
                    product.setCategory(updatedProduct.getCategory());
                    product.setQuantity(updatedProduct.getQuantity());
                    product.setImageUrl(updatedProduct.getImageUrl());
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new RuntimeException("Product with id " + id + " not found"));
    }
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product with id " + id + " not found");
        }
        productRepository.deleteById(id);
    }
}
