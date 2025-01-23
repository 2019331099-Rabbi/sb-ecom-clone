package com.ecommerce.sbecom.repositories;

import com.ecommerce.sbecom.models.Category;
import com.ecommerce.sbecom.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByCategoryId(Long categoryId, Pageable pageDetails);
    Page<Product> findByNameLikeIgnoreCase(String keyword, Pageable pageDetails);


    List<Product> findByNameLike(String keyword);
    List<Product> findByNameContaining(String keyword);
}
