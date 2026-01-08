package com.E_Commerce.Backend.Controller;

import com.E_Commerce.Backend.DTO.request.ProductRequest;
import com.E_Commerce.Backend.Entities.Product;
import com.E_Commerce.Backend.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products/")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/getproduct")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping("/addproduct/")
    public ResponseEntity<Product> addProduct(@RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.addProduct(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestBody Product product) {
        Product updated = productService.updateProduct(id, product);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }


}
