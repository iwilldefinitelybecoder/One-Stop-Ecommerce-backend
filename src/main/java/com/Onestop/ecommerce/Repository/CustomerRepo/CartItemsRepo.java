package com.Onestop.ecommerce.Repository.CustomerRepo;


import com.Onestop.ecommerce.Entity.Customer.Customer;
import com.Onestop.ecommerce.Entity.Customer.cart.Items;
import com.Onestop.ecommerce.Entity.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemsRepo extends JpaRepository<Items,Long> {

    Optional<Items> findByProductIdentifier(String productIdentifier);
    Optional<Items> findByCartIdentifier(String cartIdentifier);
    void deleteByIdentifier(String productIdentifier);
    Optional<Items> findByCartId(Long cartId);
    Optional<Items> findByIdentifier(String Identifier);
    void deleteByCartId(Long cartId);
    Optional<List<Items>> findAllByProductIdentifier(String productIdentifier);
    List<Items> findByProductAndCart_Customer(Product product, Customer customer);
}
