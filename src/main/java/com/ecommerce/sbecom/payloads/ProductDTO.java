package com.ecommerce.sbecom.payloads;

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
    private String name;
    private String image;
    private String description;
    private Integer quantity;
    private Double discount;
    private Double price;
    private Double specialPrice;
}
