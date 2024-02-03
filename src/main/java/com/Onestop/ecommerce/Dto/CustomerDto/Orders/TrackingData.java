package com.Onestop.ecommerce.Dto.CustomerDto.Orders;

import com.Onestop.ecommerce.Entity.Logistics.ShipmentAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrackingData {

    private Date timestamp;
    private ShipmentAction action;
    private String place;

}
