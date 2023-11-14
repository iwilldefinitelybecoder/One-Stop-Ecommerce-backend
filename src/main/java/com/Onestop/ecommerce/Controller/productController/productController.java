package com.Onestop.ecommerce.Controller.productController;

import com.Onestop.ecommerce.Dto.productsDto.productsDto;
import com.Onestop.ecommerce.Dto.productsDto.productsTagsDto;
import com.Onestop.ecommerce.Dto.productsDto.resourceDetailsTdo;
import com.Onestop.ecommerce.Entity.products.Product;

import com.Onestop.ecommerce.Service.products.productsServices;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.security.Principal;
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
            @RequestParam("category") String category,
            @RequestParam("stock") int stock,
            @RequestParam("regularPrice") double regularPrice,
            @RequestParam(value = "salePrice",required = false) double salePrice,
            @RequestParam("tags") List<String> tags,
            @RequestParam("images") List<MultipartFile> images

            ){

        for(String tag:tags) {
            System.out.println(tag);
        };
        var request = productsDto.builder()
                .name(name)
                .description(description)
                .category(category)
                .stock(stock)
                .regularPrice(regularPrice)
                .salePrice(salePrice !=0?salePrice:0)
                .productTypeTags(tags)
                .build();

        var imagesDto = resourceDetailsTdo.builder()
                .image(images)
                .build();


        String response1 = ProductsServices.saveProduct(request);

        String response3 = ProductsServices.saveImages(imagesDto, response1);
        log.info("response1 is {}",response1);
        log.info("response3 is {}",response3);
        if(response1!=null && response3.equals("success")) {
            return ResponseEntity.status(200).body("success");
        } else {
            return ResponseEntity.status(500).body("errorrrr");
        }







    }
 @GetMapping("/getproducts/{}")
    public ResponseEntity<?> getProducts() {
        try{
            List<Product> products = ProductsServices.getProducts();
            return ResponseEntity.status(200).body(products);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }

}
