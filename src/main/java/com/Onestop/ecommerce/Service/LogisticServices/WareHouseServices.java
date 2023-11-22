package com.Onestop.ecommerce.Service.LogisticServices;

import com.Onestop.ecommerce.Dto.Logistics.WareHouseRequest;
import com.Onestop.ecommerce.Dto.Logistics.WarehouseResponse;
import com.Onestop.ecommerce.Entity.Logistics.WareHouse;
import com.Onestop.ecommerce.Repository.LogisticsRepo.WareHouseRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public List<WarehouseResponse> getAllWareHouse(String username) {
        return null;
    }
}
