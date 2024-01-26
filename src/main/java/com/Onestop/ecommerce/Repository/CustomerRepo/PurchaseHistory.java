package com.Onestop.ecommerce.Repository.CustomerRepo;

import com.Onestop.ecommerce.Entity.Customer.Customer;
import com.Onestop.ecommerce.Entity.Customer.UserPurchaseHistory;
import com.Onestop.ecommerce.Entity.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseHistory extends JpaRepository<UserPurchaseHistory, Long> {

    List<UserPurchaseHistory> findByCustomerUserEmail(String email);
    UserPurchaseHistory findByCustomerIdAndProductIdAndOrdersId(Long customer, Long product,Long orders);


    UserPurchaseHistory findByIdentifier(String iddentifier);
}
