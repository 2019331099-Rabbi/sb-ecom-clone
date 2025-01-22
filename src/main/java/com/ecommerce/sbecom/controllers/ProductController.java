package com.ecommerce.sbecom.controllers;

import com.ecommerce.sbecom.payloads.ProductDTO;
import com.ecommerce.sbecom.payloads.ProductResponse;
import com.ecommerce.sbecom.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/api/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO, @PathVariable Long categoryId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.addProduct(productDTO, categoryId));
    }

    @GetMapping("/api/public/products")
    public ResponseEntity<ProductResponse> getAllProducts() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.getAllProducts());
    }

    @GetMapping("/api/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.searchByCategory(categoryId));
    }

    @GetMapping("/api/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String keyword) {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(productService.searchByKeyword(keyword));
    }

    @PutMapping("/api/admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId, @RequestBody ProductDTO productDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.updateProduct(productId, productDTO));
    }

    @DeleteMapping("/api/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.deleteProduct(productId));
    }

    @PutMapping("/api/admin/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId, @RequestParam(name = "image") MultipartFile image) throws IOException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.updateProductImage(productId, image));
    }
}
