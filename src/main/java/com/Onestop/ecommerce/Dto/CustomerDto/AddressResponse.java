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
    private String address;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private String street;
    private String identifier;
}
