package com.ecommerce.sbecom.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
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
}
