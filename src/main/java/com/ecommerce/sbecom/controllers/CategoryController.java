package com.ecommerce.sbecom.controllers;

import com.ecommerce.sbecom.config.AppConstants;
import com.ecommerce.sbecom.payloads.CategoryDTO;
import com.ecommerce.sbecom.payloads.CategoryResponse;
import com.ecommerce.sbecom.services.CategoryServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {
    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    @GetMapping("/api/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER, required = false) String sortOrder
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryServiceImpl.getAllCategories(pageNumber, pageSize, sortBy, sortOrder));
    }

    @PostMapping("/api/admin/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryServiceImpl.createCategory(categoryDTO));
    }

    @DeleteMapping("/api/admin/categories/{id}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryServiceImpl.deleteCategory(id));
    }

    @PutMapping("/api/admin/categories/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryServiceImpl.updateCategory(id, categoryDTO));
    }
}
