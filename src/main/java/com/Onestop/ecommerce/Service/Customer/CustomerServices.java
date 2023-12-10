package com.Onestop.ecommerce.Service.Customer;

import com.Onestop.ecommerce.Dto.CustomerDto.AddressResponse;
import com.Onestop.ecommerce.Entity.Customer.Customer;
import com.Onestop.ecommerce.Entity.Customer.cart.Cart;
import com.Onestop.ecommerce.Entity.Customer.cart.WishList;
import com.Onestop.ecommerce.Entity.user.userEntity;
import com.Onestop.ecommerce.Exceptions.CustomerNotFoundException;
import com.Onestop.ecommerce.Repository.CustomerRepo.CartRepo;
import com.Onestop.ecommerce.Repository.CustomerRepo.CustomerRepo;
import com.Onestop.ecommerce.Repository.CustomerRepo.WishListRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerServices {

    private final CustomerRepo customerRepo;
    private final CartRepo cartRepo;

    private final CartServices cartServices;
    private final WishListRepo wishListRepo;

    @Transactional
    public String saveCustomer(userEntity user){
        var customer = Customer.builder()
                .user(user)
                .address(new ArrayList<>())
                .paymentCards(new ArrayList<>())
                .build();
        var cart = Cart.builder()
                .customer(customer)
                .build();
        WishList wishList = WishList.builder()
                .customer(customer)
                .product(new ArrayList<>())
                .build();
        customer.setCart(cart);
        customer.setWishList(wishList);
        wishListRepo.save(wishList);
        cartRepo.save(cart);
        customerRepo.save(customer);
        return "Customer saved successfully";

    }

    public Customer getCustomer(String email){
        var customer = customerRepo.findByUserEmail(email);
        if(customer == null){
            log.error("Customer not found");
            throw new CustomerNotFoundException("CUSTOMER_NOT_FOUND");
        }
        return customer;
    }

    public void updateCustomer(Customer customer){
        customerRepo.save(customer);
    }

    public List<AddressResponse> getCustomerAddress(String email){
     Customer customer  = getCustomer(email);
     List<AddressResponse> responses = new ArrayList<>();
        customer.getAddress().forEach(address -> {
            var response = AddressResponse.builder()
                    .addressId(address.getIdentifier())
                    .city(address.getCity())
                    .country(address.getCountry())
                    .phone(address.getPhone())
                    .locality(address.getLocality())
                    .name(address.getName())
                    .email(address.getEmail())
                    .area(address.getArea())
                    .zipCode(address.getZipCode())
                    .build();
            responses.add(response);
        });
        return  responses;

    }
}
