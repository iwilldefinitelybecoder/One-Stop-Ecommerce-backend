package com.Onestop.ecommerce.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetails {
    private String name;
    private String description;
    private String RegularPrice;
    private String SalePrice;
    private String category;
    private BigInteger Stock;
    private List<String> tags;
    private List<MultipartFile> images;
}
