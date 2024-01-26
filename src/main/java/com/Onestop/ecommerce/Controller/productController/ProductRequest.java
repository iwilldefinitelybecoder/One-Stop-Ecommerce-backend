package com.Onestop.ecommerce.Controller.productController;

import com.Onestop.ecommerce.Entity.products.MetaAttribute;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
    private Long productId;
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    private MetaAttribute attributes;
    private double regularPrice;
    private double salePrice;

}
