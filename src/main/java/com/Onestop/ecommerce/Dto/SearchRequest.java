package com.Onestop.ecommerce.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest {
        private String keyword;
        private String category;
        private Double[] priceRange;
        private Double averageRating;
        private int page;
        private int size = 10;
}
