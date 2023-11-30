package com.Onestop.ecommerce.Dto.VendorDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendorProductList {
    private String productId;
    private String name;
    private Date InnDate;
    private double regularPrice;
    private double salePrice;
    private Integer Stock;
    private boolean isEnabled;
    private String thumbnail;
}
