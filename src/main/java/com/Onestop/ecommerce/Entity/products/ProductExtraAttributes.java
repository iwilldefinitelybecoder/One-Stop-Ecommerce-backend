package com.Onestop.ecommerce.Entity.products;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document(collection = "product-extra-attributes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductExtraAttributes {
    @Id
    private String id;
    private String productId;
    private Map<String,Object> extraAttributes;
}
