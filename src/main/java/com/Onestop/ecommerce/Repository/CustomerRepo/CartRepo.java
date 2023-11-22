package com.Onestop.ecommerce.Repository.CustomerRepo;

import com.Onestop.ecommerce.Entity.Customer.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<Cart,Long> {
    @Query("select c from Cart c JOIN c.customer cu JOIN cu.user u where u.email = :email")
    Cart findCartByUserEmail(@Param("email") String email);

    Optional<Cart> findByIdentifier(String Identifier);
}
