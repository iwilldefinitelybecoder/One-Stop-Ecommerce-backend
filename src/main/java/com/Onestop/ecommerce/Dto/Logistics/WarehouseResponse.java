package com.Onestop.ecommerce.Dto.Logistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseResponse {
    private String warehouseId;
    private String wareHouseLocation;
    private String wareHouseName;
    private Long contactNumber;
    private String email;
    private Long capacity;
    private Long storageLeft;
    private List<InventoryResponse> inventoryResponseList;
}
