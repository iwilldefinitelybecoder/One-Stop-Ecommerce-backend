package com.Onestop.ecommerce.Repository.products;

import com.Onestop.ecommerce.Entity.products.ProductExtraAttributes;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


public interface ProductExtraAttributesRepo extends MongoRepository<ProductExtraAttributes, String>{

}
