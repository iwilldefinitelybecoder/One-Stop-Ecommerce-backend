package com.Onestop.ecommerce.Controller.productController;

import com.Onestop.ecommerce.Dto.SearchRequest;
import com.Onestop.ecommerce.Dto.productsDto.*;
import com.Onestop.ecommerce.Entity.products.MetaAttribute;
import com.Onestop.ecommerce.Entity.products.Product;

import com.Onestop.ecommerce.Events.Emmitter.DisableProductEmmitter;
import com.Onestop.ecommerce.Service.Customer.OrderServices;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.tracing.OpenTelemetryAutoConfiguration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/v1/product")
@Slf4j
@RequiredArgsConstructor
public class productController {


    private final com.Onestop.ecommerce.Service.products.ProductsServices ProductsServices;


    private final ApplicationEventPublisher eventPublisher;
    private final OrderServices orderServices;
    private final RestTemplate restTemplate = new RestTemplate();



    @PostMapping("/add")
    public ResponseEntity<?> addProduct(
            @ModelAttribute productsDto request,
            @RequestParam(value = "images",required = false) List<MultipartFile> images,
            @RequestParam(value = "thumbnailFile",required = false) MultipartFile thumbnailFile
//            @RequestParam(value = "extraAttributes",required = false) Map<String,Object> extraAttributes
            ){

//        Map<String,Object> extraAttributes1 = (Map<String, Object>) extraAttributes.get("extraAttributes");



        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
        var images1 = resourceDetailsTdo.builder()
                .image(images)
                .build();
        String response1 = ProductsServices.saveProduct(request,images1,userName,thumbnailFile);

      try
        {
            return ResponseEntity.status(200).body(response1);
        }catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }


    }

    @PostMapping("/updateProductDetails")
    public ResponseEntity<?> updateProduct(
            @ModelAttribute productsDto request,
            @RequestParam(value = "images",required = false) List<MultipartFile> images,
            @RequestParam(value = "productId") String productId,
            @RequestParam(value = "thumbnailFile",required = false) MultipartFile thumbnailFile
    ){
        try {
            String response = ProductsServices.updateProduct(request,productId,images,thumbnailFile);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
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

    @PostMapping ("/search-results")
    public ResponseEntity<?> searchResults(@RequestBody SearchRequest request) {

        try{
            Pageable pageable  = PageRequest.of(request.getPage(),request.getSize(), Sort.by("average_rating").descending());
            log.info(request.getKeyword());
            return ResponseEntity.status(200).body(ProductsServices.searchResults(request.getKeyword(),request.getCategory(),pageable,request.getAverageRating(),request.getPriceRange()));
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
    public ResponseEntity<?> addReview(@ModelAttribute ReviewRequest request,@RequestParam("reviewImages") List<MultipartFile> images) {
        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
        request.setEmail(userName);
        try{
            String response = ProductsServices.addReview(request,images);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }

    @GetMapping("/validatePurchase")
    public ResponseEntity<?> validatePurchase(@RequestParam(value = "purchaseId") String purchaseId) {
        try{

            return ResponseEntity.status(200).body(orderServices.verifyPurchase(purchaseId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }



    }
    @GetMapping("/verifyReviewExists")
    public ResponseEntity<?> doesReviewExists(@RequestParam(value = "purchaseId") String purchaseId) {
        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try{

            return ResponseEntity.status(200).body(orderServices.validateReviewExists(purchaseId,userName));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }



    }
    @GetMapping("/getWriteReviewMetaInfo")
    public ResponseEntity<?> getReviewMetaInfo(@RequestParam(value = "purchaseId") String purchaseId) {
        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try{

            return ResponseEntity.status(200).body(orderServices.getReviewMetaInfo(userName,purchaseId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }



    }
    @GetMapping("/getReviewData")
    public ResponseEntity<?> getReviewData(@RequestParam(value = "purchaseId") String purchaseId) {
        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try{

            return ResponseEntity.status(200).body(orderServices.ReviewData(userName,purchaseId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }



    }

    @GetMapping("/attributes")
    public ResponseEntity<?> getProductAttributes(@RequestParam(value = "attribute") MetaAttribute attribute) {
        try{

            return ResponseEntity.status(200).body(ProductsServices.getProductAttributes(attribute));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }

    @GetMapping("/getProductsByCategory")
    public ResponseEntity<?> getProductsByCategory(@RequestParam(value = "category") String category) {
        log.info(category);
        try{

            return ResponseEntity.status(200).body(ProductsServices.getProductsByCategory(category));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }

    @PostMapping("/updateMajorDetails")
    public ResponseEntity<?> updateProductMajorDetails(@RequestBody ProductRequest request,
                                                       @RequestParam(value = "productId") String productId) {
        try {
            String response = ProductsServices.updateProductMajorDetails(request, productId);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/getProductMajorDetails")
    public ResponseEntity<?> getProductMajorDetails(@RequestParam(value = "productId") String productId) {
        try {

            return ResponseEntity.status(200).body(ProductsServices.getProductMajorDetails((productId)));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }



    @GetMapping("/getAddributes")
    public ResponseEntity<?> getAttributes() {
        try {
            return ResponseEntity.status(200).body(ProductsServices.getProductAttributesList());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("publish")
    public ResponseEntity<?> publishProduct(@RequestParam(value = "productId") String productId) {
        try{
            String response = ProductsServices.publishProduct(productId);


            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }



    }

    @GetMapping("/getProduct")
    public ResponseEntity<?> getProduct(@RequestParam(value = "productId") String productId) {
        try {
            ProductResponse response = ProductsServices.getProduct(productId);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());

        }

    }

    @GetMapping("/getProductDetailReview")
    public ResponseEntity<?> getProductDetailReview(@RequestParam(value = "productId") String productId) {
        try {

            return ResponseEntity.status(200).body(ProductsServices.getProductDetailReview(productId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());

        }

    }

    @GetMapping("/getEditProductDetails")
    public ResponseEntity<?> getEditProductDetails(@RequestParam(value = "productId") String productId) {
        log.info(productId);
        try {

            return ResponseEntity.status(200).body(ProductsServices.getEditProductDetails(productId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());

        }

    }


}
