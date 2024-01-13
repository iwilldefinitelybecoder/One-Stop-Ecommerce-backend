package com.Onestop.ecommerce.Dto.productsDto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
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
    private String brand;
    private String wareHouseId;
    private List<MultipartFile> images;
//    private Map<String, Object> extraAttributes = new HashMap<>();
//
//    @JsonAnySetter
//    public void setExtraAttributes(String key, Object value) {
//        this.extraAttributes.put(key, value);
//    }
//
//    public void setExtraAttributes(String extraAttributes) {
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            this.extraAttributes = mapper.readValue(extraAttributes, new TypeReference<Map<String, Object>>() {
//            });
//        } catch (Exception e) {
//            // Handle exception if JSON parsing fails
//            e.printStackTrace();
//        }
//
//    }
}
