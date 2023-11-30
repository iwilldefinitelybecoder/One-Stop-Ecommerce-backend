package com.Onestop.ecommerce.Service.LogisticServices;

import com.Onestop.ecommerce.Dto.Logistics.WareHouseRequest;
import com.Onestop.ecommerce.Dto.Logistics.WarehouseResponse;

import java.util.List;

public interface WareHouseService {
    String addWareHouse(WareHouseRequest wareHouseRequest, String username);
    String updateWareHouse(WareHouseRequest wareHouseRequest, String username);
    String deleteWareHouse(String wareHouseId, String username);
    WarehouseResponse getWareHouse(String wareHouseId, String username);
    List<WarehouseResponse> getAllWareHouse();


}
