package com.Onestop.ecommerce.Service.products;

import com.Onestop.ecommerce.Controller.productController.ProductRequest;
import com.Onestop.ecommerce.Dto.productsDto.ProductResponse;
import com.Onestop.ecommerce.Dto.productsDto.ReviewRequest;
import com.Onestop.ecommerce.Dto.productsDto.productsDto;
import com.Onestop.ecommerce.Dto.productsDto.resourceDetailsTdo;
import com.Onestop.ecommerce.Entity.Customer.Customer;
import com.Onestop.ecommerce.Entity.Customer.cart.Cart;
import com.Onestop.ecommerce.Entity.Customer.cart.Items;
import com.Onestop.ecommerce.Entity.Customer.cart.WishList;
import com.Onestop.ecommerce.Entity.Logistics.ProductInventory;
import com.Onestop.ecommerce.Entity.Logistics.WareHouse;
import com.Onestop.ecommerce.Entity.products.Product;
import com.Onestop.ecommerce.Entity.products.Review;
import com.Onestop.ecommerce.Entity.products.resourceDetails;
import com.Onestop.ecommerce.Entity.vendor.Vendor;
import com.Onestop.ecommerce.Events.Emmitter.DisableProductEmmitter;
import com.Onestop.ecommerce.Repository.CustomerRepo.CartItemsRepo;
import com.Onestop.ecommerce.Repository.CustomerRepo.CartRepo;
import com.Onestop.ecommerce.Repository.CustomerRepo.WishListRepo;
import com.Onestop.ecommerce.Repository.LogisticsRepo.InventoryRepo;
import com.Onestop.ecommerce.Repository.LogisticsRepo.WareHouseRepo;
import com.Onestop.ecommerce.Repository.VendorRepo.VendorRepository;
import com.Onestop.ecommerce.Repository.products.*;
import com.Onestop.ecommerce.Service.Customer.CustomerServices;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class ProductsServices implements productServices {


    @Autowired
    private productsRepo productsRepo;

    @Autowired
    private resourceRepo resourceRepo;

    @Autowired
    private tagsRepo tagsRepo;

    @Autowired
    VendorRepository vendorRepo;

    @Autowired
    WareHouseRepo wareHouseRepo;

    @Autowired
    InventoryRepo inventoryRepo;

    @Autowired
    private ReviewsRepo reviewsRepo;

    @Autowired
    private SpecialAttributesRepo specialAttributesRepo;

    @Autowired
    private ProductExtraAttributesRepo productExtraAttributesRepo;

    @Autowired
    private CustomerServices customerServices;

    @Autowired
    private CartItemsRepo cartItemsRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private WishListRepo wishListRepo;

    private Vendor getVendor(String email){
        return vendorRepo.findByUserEmail(email).orElseThrow(()-> new RuntimeException("vendor not found"));
    }
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Override
    @Transactional
    public String saveProduct(productsDto request,resourceDetailsTdo images,String email) {
        var vendor = getVendor(email);
//        var extraAttributes = ProductExtraAttributes.builder()
//                .extraAttributes(request.getExtraAttributes())
//                .build();
//        String newAttributeId = productExtraAttributesRepo.save(extraAttributes).getId();
        WareHouse wareHouse = wareHouseRepo.findByIdentifier(request.getWareHouseId()).orElseThrow(()->{throw new RuntimeException("No warehouse exists");});
        var product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .stock(request.getStock())
                .vendor(vendor)
                .wareHouse(wareHouse)
                .reviews(new ArrayList<>())
//                .extraAttributesId(newAttributeId)
                .regularPrice(request.getRegularPrice())
                .productTypeTags(request.getProductTypeTags())
                .salePrice(request.getSalePrice() !=0?request.getSalePrice():0)
                .build();
        var inventoryProduct = ProductInventory.builder()
                .product(product)
                .vendor(vendor)
                .wareHouse(wareHouse)
                .inDate(new Date())
                .Stock(request.getStock())
                .build();
        product.setProductInventory(inventoryProduct);
        Product product1 = saveImages(images,product);
        wareHouse.getInventory().add(inventoryProduct);
        try {
            productsRepo.save(product1);
            inventoryRepo.save(inventoryProduct);
            wareHouseRepo.save(wareHouse);
            return product.getId().toString();
        } catch (Exception e) {
            log.error(e.getMessage());
            return "error";
        }
    }



    @Override
    public Product findProductById(Long id) {
        return productsRepo.findById(id).get();
    }

    @Override
    public Product saveImages(resourceDetailsTdo images, Product product) {
        boolean success = true; // Initialize a boolean variable to track success
        List<resourceDetails> ResourceDetails = new ArrayList<>();
        for (MultipartFile image : images.getImage()) {
            try {
                HashMap response = SaveImageTOFs(image);
                if (response.get("originalFileName") != null && response.get("destination") != null) {
                    log.info("saving image");
                   var resource = resourceDetails.builder()
                                   .name(response.get("originalFileName").toString())
                                           .url(response.get("newFileName").toString())
                                             .product(product)
                                                .build();

                    resourceRepo.save(resource);
                    ResourceDetails.add(resource);

                } else {
                    // Handle the case where image details are missing or invalid
                    success = false;

                }
            } catch (IOException e) {
                e.printStackTrace();
                success = false; // Set success to false in case of an exception

            }
        }
        product.setImages(ResourceDetails);
        product.setThumbnail(ResourceDetails.get(0));
        // After the loop, return the result based on the success variable
        if (success) {
            log.info("success");
            return product;
        }
        else {
            log.info("error");
            return null;
        }
    }

    @Override
    public List<ProductResponse> getProducts() {
        List<ProductResponse> products = new ArrayList<>();
        productsRepo.findAll().forEach(product -> {
           if (!product.isEnabled()) {
               return;
           };
            var inventory = inventoryRepo.findByProductIdentifier(product.getIdentifier()).orElseThrow(()->new RuntimeException("product Not Found"));
            var response = ProductResponse.builder()
                    .name(product.getName())
                    .description(product.getDescription())
                    .category(product.getCategory())
                    .regularPrice(product.getRegularPrice())
                    .imageURL(parseImageURL(product.getImages()))
                    .productId(product.getIdentifier())
                    .isPublished(product.isEnabled())
                    .innDate(inventory.getInDate())
                    .numberOfRatings(product.getReviews().size())
                    .rating(product.getAverageRating())
                    .build();
            if(product.getSalePrice() != 0){
                response.setSalePrice(product.getSalePrice());
            }
            products.add(response);
        });
        return products;
    }

    @Override
    public List<String> searchProducts(String keyword,String category) {
        if(keyword == null || keyword.isBlank()) {
            return null;
        }
        if(category == null || category.isBlank()) {
            category = "";
        }
        log.info("keyword: {}",keyword);
        List<Product> products = productsRepo.findProductsByRegex(keyword,category);
        log.info("products: {}",products.get(0).getName());
        return products.stream().map(Product::getName).toList();
    }

   private Set<String> generateNGrams(String input, int n) {
        Set<String> ngrams = new HashSet<>();
        for (int i = 0; i < input.length() - n + 1; i++) {
            String ngram = input.substring(i, i + n);
            ngrams.add(ngram);
        }
        return ngrams;
    }


    private double calculateSimilarity(String str1, String str2, int n) {
        Set<String> ngrams1 = generateNGrams(str1, n);
        Set<String> ngrams2 = generateNGrams(str2, n);

        // Calculate the Jaccard similarity between the sets of n-grams
        Set<String> union = new HashSet<>(ngrams1);
        union.addAll(ngrams2);

        Set<String> intersection = new HashSet<>(ngrams1);
        intersection.retainAll(ngrams2);

        return (double) intersection.size() / union.size();
    }





    @Override
    public List<Product> searchResults(String keyword, String category) {
        if(keyword == null || keyword.isBlank()) {
            return null;
        }
        if(category == null || category.isBlank()) {
            category = "";
        }
        List<Product> productsList = productsRepo.findProductsByRegex(keyword,category);

        List<Product> products = productsRepo.findProductsByRegex(keyword,category);
                products.forEach(product -> {
                var response = ProductResponse.builder()
                        .name(product.getName())
                        .description(product.getDescription())
                        .category(product.getCategory())
                        .regularPrice(product.getRegularPrice())
                        .imageURL(parseImageURL(product.getImages()))
                        .productId(product.getIdentifier())
                        .numberOfRatings(product.getReviews().size())
                        .rating(product.getAverageRating())
                        .build();
                if(product.getSalePrice() != 0){
                    response.setSalePrice(product.getSalePrice());
                }
                productsList.add(product);
        });


        return productsList;
    }


    @Override
    public String addReview(ReviewRequest request) {
        var product = productsRepo.findByIdentifier(request.getProductId()).orElseThrow(()-> new RuntimeException("product not found"));
        var customer = customerServices.getCustomer(request.getEmail());
        var review = Review.builder()
                .product(product)
                .rating(request.getRating())
                .review(request.getReview())
                .customer(customer)
                .date(new Date())
                .build();
        product.getReviews().add(review);
        productsRepo.save(product);
        return "success";
    }



    @Override
    public List<ReviewRequest> getAllProductReviews(String productId) {
        var reviews = reviewsRepo.findAllByProductIdentifier(productId);
        var response = new ArrayList<ReviewRequest>();
        reviews.forEach(review -> {
            var reviewRequest = ReviewRequest.builder()
                    .email(review.getCustomer().getUser().getEmail())
                    .rating(review.getRating())
                    .review(review.getReview())
                    .build();
            response.add(reviewRequest);
        });
        return response;
    }

    @Override
    public List<Product> getProductAttributes(String attribute) {
        List<Product> products = specialAttributesRepo.findAllProductByAttributesAndIsActive(attribute,true);

        return products.stream()
                .sorted((Comparator.comparingDouble(Product::getAverageRating).reversed()))
                .collect(Collectors.toList());
    }

    @Override
    public String deleteProduct(String productId) {
       return null;
    }

    @Override
    public ProductResponse getProductById(String productId) {
        return null;
    }

    @Override
    public ProductResponse updateProduct(ProductRequest request, String productId) {
        return null;
    }

    @Override
    public String publishProduct(String productId) {
        productsRepo.findByIdentifier(productId).ifPresentOrElse(product -> {
            if(product.isEnabled()){
                eventPublisher.publishEvent(new DisableProductEmmitter(productId));
            }

            product.setEnabled(!product.isEnabled());
            productsRepo.save(product);

        },()->{
            throw new RuntimeException("product not found");
        });
        return "success";
    }

    @Transactional
    @Override
    public String removeDisabledProduct(String productId) {
        var product = productsRepo.findByIdentifier(productId).orElseThrow(()-> new RuntimeException("product not found"));
        var cartItems = cartItemsRepo.findAllByProductIdentifier(productId).orElseThrow(()-> new RuntimeException("product not found"));
        for(Items item:cartItems){
            Customer customer = item.getCart().getCustomer();
            log.info("item deleted");
            Cart cart = customer.getCart();
            WishList wishList = customer.getWishList();
            wishList.getProduct().add(item.getProduct());
            cart.getItems().remove(item);
            cartItemsRepo.delete(item);
            cartRepo.save(cart);
            wishListRepo.save(wishList);

        }
        return "success";
    }

    private List<String> parseImageURL(List<resourceDetails> images){
        List<String> imageURL = new ArrayList<>();
        images.forEach(image -> {
            var url = "http://localhost:8000/image-resources/product-Images/" + image.getUrl();
            imageURL.add(url);
        });
        return imageURL;
    }



    public  HashMap SaveImageTOFs(MultipartFile image) throws IOException {
        String userHome = System.getProperty("user.home");
        String downloadsDir = userHome + "\\Downloads\\product-Images\\";
        Path directoryPath = Path.of(downloadsDir);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        try {
            String originalFileName = image.getOriginalFilename();
            String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;
            File destination = new File(downloadsDir + uniqueFileName);

            HashMap<String, String> response = new HashMap<>();
            response.put("originalFileName", originalFileName);
            response.put("destination", destination.getAbsolutePath());
            response.put("newFileName", uniqueFileName);
            image.transferTo(destination);

            // Return a success response
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception and return an error response
            return null;
        }
    }


}
