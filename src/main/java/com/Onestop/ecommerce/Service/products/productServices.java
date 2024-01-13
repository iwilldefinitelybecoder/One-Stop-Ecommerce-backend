package com.Onestop.ecommerce.Service.products;

import com.Onestop.ecommerce.Controller.productController.ProductRequest;
import com.Onestop.ecommerce.Dto.productsDto.*;
import com.Onestop.ecommerce.Entity.products.MetaAttribute;
import com.Onestop.ecommerce.Entity.products.Product;
import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface productServices {


    String saveProduct(productsDto request, resourceDetailsTdo images, String email, Map<String,Object> extraAttributes);






    Product saveImages(resourceDetailsTdo images, Product product);

    List<ProductResponse> getProducts();

    List<String> searchProducts(String keyword,String category);

    List<ProductResponse> searchResults(String keyword, String category);

    String addReview(ReviewRequest request);


    List<ReviewRequest> getAllProductReviews(String productId);

    List<Product> getProductAttributes(String attribute);


    String updateProduct(productsDto request, String productId, List<MultipartFile> images);

    String publishProduct(String productId);

    @Transactional
    String removeDisabledProduct(String productId);

    ProductResponse getProduct(String productId);

    List<MetaAttribute> getProductAttributesList();


    String updateProductMajorDetails(ProductRequest request, String productId);
}
