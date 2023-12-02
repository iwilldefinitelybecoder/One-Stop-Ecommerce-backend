package com.Onestop.ecommerce.Dto.CustomerDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {
    private String name;
    private String email;
    private Long   number;
    private String city;
    private String country;
    private int zipCode;
    private String area;
    private String locality;
    private String addressId;
}
