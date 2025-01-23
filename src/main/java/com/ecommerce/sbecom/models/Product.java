package com.ecommerce.sbecom.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(min = 2, max = 30, message = "Product name must be between 2 and 30 characters")
    private String name;
    private String image;
    @Size(min = 5, max = 200, message = "Product description must be between 5 and 200 characters")
    private String description;
    private Integer quantity;
    private Double discount;
    private Double price;
    private Double specialPrice;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    public void setSpecialPrice(Double price, Double discount) {
        this.specialPrice = price - (discount / 100.0) * price;
    }

    public void setCategory(Category category) {
        this.category = category;
        if (category != null) {
            category.getProducts().add(this);
        }
    }
}
