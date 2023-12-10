package com.Onestop.ecommerce.Dto.productsDto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class productsDto {
    private String name;
    private String description;
    private String category;
    private Integer stock;
    private double regularPrice;
    private List<String> productTypeTags;
    private double salePrice;
    private String wareHouseId;
//    private Map<Object,?> extraAttributes;

//    public void setExtraAttributes(String extraAttributes) {
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            this.extraAttributes = mapper.readValue(extraAttributes, new TypeReference<Map<String, Object>>() {});
//        } catch (Exception e) {
//            // Handle exception if JSON parsing fails
//            e.printStackTrace();
//        }

}
