package com.Onestop.ecommerce.Controller.productController;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
    private Long userId;
    private Long productId;
    private Long quantity;
}
