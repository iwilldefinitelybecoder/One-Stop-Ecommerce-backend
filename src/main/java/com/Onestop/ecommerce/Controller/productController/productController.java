package com.Onestop.ecommerce.Controller.productController;

import com.Onestop.ecommerce.Dto.productsDto.ProductResponse;
import com.Onestop.ecommerce.Dto.productsDto.ReviewRequest;
import com.Onestop.ecommerce.Dto.productsDto.productsDto;
import com.Onestop.ecommerce.Dto.productsDto.resourceDetailsTdo;
import com.Onestop.ecommerce.Entity.products.Product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/v1/product")
@Slf4j
public class productController {

    @Autowired
    private com.Onestop.ecommerce.Service.products.ProductsServices ProductsServices;

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(
            @ModelAttribute productsDto request,
            @RequestParam(value = "images",required = false) List<MultipartFile> images
            ){




        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
        var images1 = resourceDetailsTdo.builder()
                .image(images)
                .build();
        String response1 = ProductsServices.saveProduct(request,images1,userName);

      try
        {
            return ResponseEntity.status(200).body(response1);
        }catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }







    }
 @GetMapping("/getproducts")
    public ResponseEntity<?> getProducts() {
        try{
            List<ProductResponse> products = ProductsServices.getProducts();
            return ResponseEntity.status(200).body(products);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProducts(@RequestParam(value = "keyword",required = false) String keyword
                                            ,@RequestParam(value = "category",required = false) String category) {
        try{
            List<String> products = ProductsServices.searchProducts(keyword,category);
            return ResponseEntity.status(200).body(products);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }

    @GetMapping("/search-results")
    public ResponseEntity<?> searchResults(@RequestParam(value = "keyword",required = false) String keyword
                                            ,@RequestParam(value = "category",required = false) String category) {
        try{
            List<Product> products = ProductsServices.searchResults(keyword,category);
            return ResponseEntity.status(200).body(products);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }

    @GetMapping("/allProductReviews")
    public ResponseEntity<?> getAllProductReviews(@RequestParam(value = "productId") String productId) {
        try{
            List<ReviewRequest> products = ProductsServices.getAllProductReviews(productId);
            return ResponseEntity.status(200).body(products);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }

    @PostMapping("/addReview")
    public ResponseEntity<?> addReview(@RequestBody ReviewRequest request) {
        try{
            String response = ProductsServices.addReview(request);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }

    @GetMapping("/product/attributes")
    public ResponseEntity<?> getProductAttributes(@RequestParam(value = "attribute") String attribute) {
        try{
            List<Product> response = ProductsServices.getProductAttributes(attribute);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }

}
