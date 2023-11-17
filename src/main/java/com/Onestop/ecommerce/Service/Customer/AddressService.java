package com.Onestop.ecommerce.Service.Customer;

import com.Onestop.ecommerce.Dto.CustomerDto.AddressRequest;
import com.Onestop.ecommerce.Dto.CustomerDto.AddressResponse;

public interface AddressService {
    String saveAddress(AddressRequest request, String email);
    String updateAddress(AddressRequest request, String identifier);
    String deleteAddress(String AddressId, String email);
    AddressResponse getAddress(String identifier);
}
