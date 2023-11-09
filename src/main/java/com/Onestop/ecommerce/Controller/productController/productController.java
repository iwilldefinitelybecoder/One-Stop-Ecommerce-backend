package com.Onestop.ecommerce.Controller.productController;

import com.Onestop.ecommerce.Entity.products.Product;

import com.Onestop.ecommerce.Service.products.productsServices;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;


@RestController
@RequestMapping("api/v1/product")
@Slf4j
public class productController {

    @Autowired
    private productsServices ProductsServices;

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("regularPrice") double price,
            @RequestParam("category") String category,
            @RequestParam("stock") int stock,
            @RequestParam("tags") List<String> tags,
            @RequestParam("images") List<MultipartFile> images,
            @RequestParam("salePrice") double salePrice
    ) {
        log.info("Name is {}", name);
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setRegularPrice(price);
        product.setCategory(category);
        product.setStock(stock);

        if(salePrice != 0) {
            product.setSalePrice(salePrice);
        }





        String response1 = ProductsServices.saveProduct(product);
        String response2 = ProductsServices.saveTags(tags, product.getId());
        String response3 = ProductsServices.saveImages(images, product.getId());

        if(response1.equals("success") && response2.equals("success") && response3.equals("success")) {
            return ResponseEntity.status(200).body("success");
        } else {
            return ResponseEntity.status(500).body("error");
        }







    }
 @GetMapping("/getproducts")
    public ResponseEntity<?> getProducts() {
        try{
            List<Product> products = ProductsServices.getProducts();
            return ResponseEntity.status(200).body(products);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }

}
