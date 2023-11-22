package com.Onestop.ecommerce.Service.products;

import com.Onestop.ecommerce.Controller.productController.ProductRequest;
import com.Onestop.ecommerce.Dto.productsDto.productsDto;
import com.Onestop.ecommerce.Dto.productsDto.productsTagsDto;
import com.Onestop.ecommerce.Dto.productsDto.resourceDetailsTdo;
import com.Onestop.ecommerce.Entity.products.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface productServices {


    String saveProduct(productsDto request,String email);



    Product findProductById(Long id);


    String saveImages(resourceDetailsTdo images, String ids);

    List<Product> getProducts();
}
