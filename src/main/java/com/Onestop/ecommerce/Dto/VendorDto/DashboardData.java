package com.Onestop.ecommerce.Dto.VendorDto;

import com.Onestop.ecommerce.utils.DateObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardData {
    private List<DateObject> salesData;
    private Double earnings;
    private Double vendorRatings;
    private Date from;
    private Date to;
    private Integer pendingOrders;
}
