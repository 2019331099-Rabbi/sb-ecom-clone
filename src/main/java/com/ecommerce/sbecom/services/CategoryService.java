package com.ecommerce.sbecom.services;

import com.ecommerce.sbecom.models.Category;
import com.ecommerce.sbecom.payloads.CategoryDTO;
import com.ecommerce.sbecom.payloads.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO deleteCategory(Long id);
    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);
}
