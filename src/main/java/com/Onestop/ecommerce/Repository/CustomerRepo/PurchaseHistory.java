package com.Onestop.ecommerce.Repository.CustomerRepo;

import com.Onestop.ecommerce.Entity.Customer.UserPurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseHistory extends JpaRepository<UserPurchaseHistory, Long> {

    List<UserPurchaseHistory> findByCustomerUserEmail(String email);
}
