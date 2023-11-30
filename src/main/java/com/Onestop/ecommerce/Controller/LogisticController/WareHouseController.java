package com.Onestop.ecommerce.Controller.LogisticController;

import com.Onestop.ecommerce.Service.LogisticServices.WareHouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/logistic/warehouse")
@RequiredArgsConstructor
public class WareHouseController {

    private final WareHouseService wareHouseService;

    @GetMapping("/getallwarehouse")
    public ResponseEntity<?> getAllWareHouse(){
        try
        {
            return ResponseEntity.ok(wareHouseService.getAllWareHouse());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
