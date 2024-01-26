package com.Onestop.ecommerce.Dto.CustomerDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private List<String> images;
    private String reviewText;
    private String headline;
    private int rating;
    private String purchaseId;
    private String productImage;
    private String productName;
    private String productId;
    private Date purchaseDate;
}
