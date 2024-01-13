package com.Onestop.ecommerce.Repository.TransactionRepo;

import com.Onestop.ecommerce.Entity.Payments.PaymentLedger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentLedeger extends JpaRepository<PaymentLedger, Long> {
    List<PaymentLedger> findByCustomerUserEmail(String email);

}
