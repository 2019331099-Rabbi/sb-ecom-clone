package com.ecommerce.sbecom.repositories;

import com.ecommerce.sbecom.models.Category;
import com.ecommerce.sbecom.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);
    List<Product> findByNameLikeIgnoreCase(String keyword);
    List<Product> findByNameLike(String keyword);

    List<Product> findByNameContaining(String keyword);
}
