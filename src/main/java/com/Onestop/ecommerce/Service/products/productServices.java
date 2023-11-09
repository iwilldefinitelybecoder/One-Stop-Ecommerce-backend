package com.Onestop.ecommerce.Service.products;

import com.Onestop.ecommerce.Controller.productController.ProductRequest;
import com.Onestop.ecommerce.Entity.products.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface productServices {



    String saveProduct(Product products);


    String saveTags(List<String> tags, Long id);

    Product findProductById(Long id);

    String saveImages(List<MultipartFile> images, Long id);

    List<Product> getProducts();
}
