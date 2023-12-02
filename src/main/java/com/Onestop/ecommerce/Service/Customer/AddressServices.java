package com.Onestop.ecommerce.Service.Customer;

import com.Onestop.ecommerce.Dto.CustomerDto.AddressRequest;
import com.Onestop.ecommerce.Dto.CustomerDto.AddressResponse;
import com.Onestop.ecommerce.Entity.Customer.Customer;
import com.Onestop.ecommerce.Entity.Customer.address.Address;
import com.Onestop.ecommerce.Exceptions.AddressNotFoundException;
import com.Onestop.ecommerce.Repository.CustomerRepo.AddressRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServices implements AddressService {

    private final AddressRepo addressRepo;
    private final CustomerServices customerServices;


    @Override
    public String saveAddress(AddressRequest request, String email) {
        try {
            Customer customer = customerServices.getCustomer(email);
            var address = Address.builder()
                    .city(request.getCity())
                    .country(request.getCountry())
                    .number(request.getNumber())
                    .area(request.getArea())
                    .locality(request.getLocality())
                    .name(request.getName())
                    .email(request.getEmail())
                    .zipCode(request.getZipCode())
                    .customer(customer)
                    .build();
            addressRepo.save(address);

            customer.getAddress().add(address);
            customerServices.updateCustomer(customer);
            return "Address saved successfully";
        }catch (Exception e) {
            return e.getMessage();
        }
    }

    public AddressResponse getAddress(String identifier){
        var address = addressRepo.findByIdentifier(identifier).orElseThrow(() -> new AddressNotFoundException("Address not found"));

        return AddressResponse.builder()
                .addressId(address.getIdentifier())
                .city(address.getCity())
                .country(address.getCountry())
                .number(address.getNumber())
                .locality(address.getLocality())
                .name(address.getName())
                .email(address.getEmail())
                .area(address.getArea())
                .zipCode(address.getZipCode())
                .build();
    }

    @Override
    public String updateAddress(AddressRequest request, String identifier) {
        var address = addressRepo.findByIdentifier(identifier).orElseThrow(() -> new AddressNotFoundException("Address not found"));
        address.setCity(request.getCity());
        address.setCountry(request.getCountry());
        address.setName(request.getName());
        address.setArea(request.getArea());
        address.setLocality(request.getLocality());
        address.setNumber(request.getNumber());
        address.setEmail(request.getEmail());
        address.setZipCode(request.getZipCode());

        addressRepo.save(address);
        return "Address updated successfully";
    }

    @Override
    @Transactional
    public String deleteAddress(String identifier, String email) {
       var address =  addressRepo.findByIdentifier(identifier);
       if(address.isEmpty()) {
           throw new AddressNotFoundException("Address not found");
       }
         addressRepo.delete(address.get());
        var customer = customerServices.getCustomer(email);
        customer.getAddress().remove(address.get());
        customerServices.updateCustomer(customer);
        return "Address deleted successfully";
    }
}
