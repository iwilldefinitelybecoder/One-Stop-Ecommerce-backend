package com.Onestop.ecommerce.Service.products;

import com.Onestop.ecommerce.Controller.productController.ProductRequest;
import com.Onestop.ecommerce.Dto.productsDto.*;
import com.Onestop.ecommerce.Entity.products.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface productServices {


    String saveProduct(productsDto request,resourceDetailsTdo images,String email);



    Product findProductById(Long id);


    Product saveImages(resourceDetailsTdo images, Product product);

    List<ProductResponse> getProducts();

    List<String> searchProducts(String keyword,String category);

    List<Product> searchResults(String keyword, String category);

    String addReview(ReviewRequest request);


    List<ReviewRequest> getAllProductReviews(String productId);

    List<Product> getProductAttributes(String attribute);
    String deleteProduct(String productId);
    ProductResponse getProductById(String productId);
    ProductResponse updateProduct(ProductRequest request, String productId);
}
