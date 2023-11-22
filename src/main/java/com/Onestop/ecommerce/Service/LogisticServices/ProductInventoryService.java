package com.Onestop.ecommerce.Service.LogisticServices;

import com.Onestop.ecommerce.Dto.Logistics.InventoryResponse;

import java.util.List;

public interface ProductInventoryService {
    public String  updateInventory(String wareHouseId,String productId, Long quantity);
    public String addInventory(String wareHouseId,String productId, Long quantity);
    public String deleteInventory(String wareHouseId,String productId);
    public List<InventoryResponse> getInventoryByWareHouse(String wareHouseId);

}
