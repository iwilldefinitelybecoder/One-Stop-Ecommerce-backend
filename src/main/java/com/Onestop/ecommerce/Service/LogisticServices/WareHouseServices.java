package com.Onestop.ecommerce.Service.LogisticServices;

import com.Onestop.ecommerce.Dto.Logistics.WareHouseRequest;
import com.Onestop.ecommerce.Dto.Logistics.WarehouseResponse;
import com.Onestop.ecommerce.Entity.Logistics.WareHouse;
import com.Onestop.ecommerce.Repository.LogisticsRepo.WareHouseRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class WareHouseServices implements WareHouseService{

    private final WareHouseRepo wareHouseRepo;
    @Override
    public String addWareHouse(WareHouseRequest wareHouseRequest, String username) {
            return null;
        }


    @Override
    public String updateWareHouse(WareHouseRequest wareHouseRequest, String username) {
        return null;
    }

    @Override
    public String deleteWareHouse(String wareHouseId, String username) {
        return null;
    }

    @Override
    public WarehouseResponse getWareHouse(String wareHouseId, String username) {
        return null;
    }

    @Override
    public List<WarehouseResponse> getAllWareHouse() {
        var wareHouse = wareHouseRepo.findAll();
        List<WarehouseResponse> wareHouseList = new ArrayList<>();
        wareHouse.forEach(wareHouse1 -> {
            var wareHouseResponse = WarehouseResponse.builder()
                    .warehouseId(wareHouse1.getIdentifier())
                    .wareHouseName(wareHouse1.getWareHouseName())
                    .storageLeft(wareHouse1.getCapacity() - wareHouse1.getInventory().size()  )
                    .build();
            wareHouseList.add(wareHouseResponse);
        });
        return wareHouseList;
    }
}
