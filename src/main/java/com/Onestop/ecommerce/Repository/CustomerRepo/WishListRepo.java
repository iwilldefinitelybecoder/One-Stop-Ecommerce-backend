package com.Onestop.ecommerce.Repository.CustomerRepo;

import com.Onestop.ecommerce.Entity.Customer.Customer;
import com.Onestop.ecommerce.Entity.Customer.cart.Items;
import com.Onestop.ecommerce.Entity.Customer.cart.WishList;
import com.Onestop.ecommerce.Entity.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepo extends JpaRepository<WishList,Long> {
    Product findByProductIdentifier(String productIdentifier);
    List<WishList> findByCustomer (Customer customer);
    void deleteByIdentifier(String productIdentifier);
    WishList findByIdentifier(String Identifier);



}
