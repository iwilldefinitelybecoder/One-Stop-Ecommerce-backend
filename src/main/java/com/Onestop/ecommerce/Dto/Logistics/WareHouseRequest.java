package com.Onestop.ecommerce.Dto.Logistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WareHouseRequest {
    private String warehouseId;
    private String wareHouseLocation;
    private String wareHouseName;
    private Long contactNumber;
    private String email;
    private Long capacity;
}
