package com.Onestop.ecommerce.Service.VendorServices;

import com.Onestop.ecommerce.Controller.vendor.VendorLoginResponse;
import com.Onestop.ecommerce.Controller.vendor.VendorRequest;
import com.Onestop.ecommerce.Dto.CustomerDto.AddressRequest;
import com.Onestop.ecommerce.Dto.VendorDto.VendorProductList;
import com.Onestop.ecommerce.Dto.productsDto.ProductResponse;

import java.util.List;

public interface VendorService {
    String register(VendorRequest request);
    VendorLoginResponse authenticate(String email);

    List<VendorProductList> getAllProducts(String userName);

    String deleteProduct(String identifier, String userName);

    String updateProduct(AddressRequest address, String identifier);

    ProductResponse getProductDetails(String identifier);
}
