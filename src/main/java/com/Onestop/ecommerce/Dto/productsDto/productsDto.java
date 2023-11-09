package com.Onestop.ecommerce.Dto.productsDto;

import jakarta.persistence.Entity;
import lombok.Data;

import java.util.List;

@Data
public class productsDto {
    private Long id;
    private String name;
    private String description;
    private String category;
    private int stock;
    private double regularPrice;
    private String salePrice;
    private List<productsTagsDto> tags;
    private List<resourceDetailsTdo> images;
}
