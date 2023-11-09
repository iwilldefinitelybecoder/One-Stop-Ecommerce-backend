package com.Onestop.ecommerce.Dto.productsDto;

import lombok.Data;

import java.util.List;

@Data
public class productsTagsDto {
    private Long id;
    private List<String> name;
}
