package com.Onestop.ecommerce.Dto.productsDto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class productsDto {
    private String name;
    private String description;
    private String category;
    private int stock;
    private double regularPrice;
    private List<String> productTypeTags;
    private double salePrice;
    private String wareHouseId;

}
