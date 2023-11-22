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
                    .state(request.getState())
                    .street(request.getStreet())
                    .zipCode(request.getZipCode())
                    .address(request.getAddress())
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
                .identifier(address.getIdentifier())
                .city(address.getCity())
                .country(address.getCountry())
                .state(address.getState())
                .street(address.getStreet())
                .zipCode(address.getZipCode())
                .build();
    }

    @Override
    public String updateAddress(AddressRequest request, String identifier) {
        var address = addressRepo.findByIdentifier(identifier).orElseThrow(() -> new AddressNotFoundException("Address not found"));
        address.setCity(request.getCity());
        address.setCountry(request.getCountry());
        address.setState(request.getState());
        address.setStreet(request.getStreet());
        address.setZipCode(request.getZipCode());
        address.setAddress(request.getAddress());
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
