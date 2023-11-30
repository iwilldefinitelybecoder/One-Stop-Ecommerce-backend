package com.Onestop.ecommerce.Repository.CustomerRepo.OrdersRepo;

import com.Onestop.ecommerce.Entity.orders.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepo extends JpaRepository<Orders,Long> {

    Optional<Orders> findByIdentifier(String orderIdentifier);
    @Query("select o from Orders o  JOIN o.customer c JOIN c.user u where u.email = :email")
    Optional<List<Orders>> findAllByUserEmail(@Param("email") String email);
}
