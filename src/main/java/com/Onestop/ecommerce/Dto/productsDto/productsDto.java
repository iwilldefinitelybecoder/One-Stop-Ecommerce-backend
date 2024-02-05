package com.Onestop.ecommerce.Dto.productsDto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.BeanMap;
import org.json.JSONObject;
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
    private List<String> tags;
    private double salePrice;
    private String brand;
    private String wareHouseId;
    private List<String> image;
    private String thumbnail;
    private Map<String,Object> extraObjects;
    private List<String> existingImages;
    private String extraAttributes;



}
