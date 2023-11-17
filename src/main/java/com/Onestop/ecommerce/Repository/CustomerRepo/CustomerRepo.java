package com.Onestop.ecommerce.Repository.CustomerRepo;

import com.Onestop.ecommerce.Entity.Customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long> {

    Customer findByUserEmail(String email);
}
