package com.Onestop.ecommerce.Service.LogisticServices;

import com.Onestop.ecommerce.Dto.Logistics.InventoryResponse;

import java.util.List;

public class ProductInventoryServices implements ProductInventoryService{
    @Override
    public String updateInventory(String wareHouseId, String productId, Long quantity) {
        return null;
    }

    @Override
    public String addInventory(String wareHouseId, String productId, Long quantity) {
        return null;
    }

    @Override
    public String deleteInventory(String wareHouseId, String productId) {
        return null;
    }

    @Override
    public List<InventoryResponse> getInventoryByWareHouse(String wareHouseId) {
        return null;
    }
}
