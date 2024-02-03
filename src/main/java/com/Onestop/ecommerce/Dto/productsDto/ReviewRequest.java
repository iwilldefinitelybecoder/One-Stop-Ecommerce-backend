package com.Onestop.ecommerce.Dto.productsDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRequest {
    private String productId;
    private String purchaseId;
    private String review;
    private int rating;
    private String headline;
    private String firstName;
    private String lastName;
    private Date date;
    private Long ProfileIconId;
    private String email;
    private List<String> images;

}
