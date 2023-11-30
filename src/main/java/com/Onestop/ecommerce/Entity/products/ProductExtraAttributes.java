package com.Onestop.ecommerce.Entity.products;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document(collection = "product-extra-attributes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductExtraAttributes {
    @Id
    private String id;
    private Map<Object,?> extraAttributes;
}
