package com.Onestop.ecommerce.Service.products;

import com.Onestop.ecommerce.Controller.productController.ProductRequest;
import com.Onestop.ecommerce.Dto.productsDto.*;
import com.Onestop.ecommerce.Entity.Customer.Customer;
import com.Onestop.ecommerce.Entity.Customer.cart.Cart;
import com.Onestop.ecommerce.Entity.Customer.cart.Items;
import com.Onestop.ecommerce.Entity.Customer.cart.WishList;
import com.Onestop.ecommerce.Entity.Logistics.ProductInventory;
import com.Onestop.ecommerce.Entity.Logistics.WareHouse;
import com.Onestop.ecommerce.Entity.UserMessages.MessageAction;
import com.Onestop.ecommerce.Entity.UserMessages.MessageStatus;
import com.Onestop.ecommerce.Entity.products.*;
import com.Onestop.ecommerce.Entity.user.userEntity;
import com.Onestop.ecommerce.Entity.vendor.SalesData;
import com.Onestop.ecommerce.Entity.vendor.Vendor;
import com.Onestop.ecommerce.Events.Emmitter.DisableProductEmmitter;
import com.Onestop.ecommerce.Events.Emmitter.MessageEmitter;
import com.Onestop.ecommerce.Repository.CustomerRepo.CartItemsRepo;
import com.Onestop.ecommerce.Repository.CustomerRepo.CartRepo;
import com.Onestop.ecommerce.Repository.CustomerRepo.PurchaseHistory;
import com.Onestop.ecommerce.Repository.CustomerRepo.WishListRepo;
import com.Onestop.ecommerce.Repository.LogisticsRepo.InventoryRepo;
import com.Onestop.ecommerce.Repository.LogisticsRepo.WareHouseRepo;
import com.Onestop.ecommerce.Repository.VendorRepo.SalesRepo;
import com.Onestop.ecommerce.Repository.VendorRepo.VendorRepository;
import com.Onestop.ecommerce.Repository.products.*;
import com.Onestop.ecommerce.Service.Customer.CustomerServices;
import com.Onestop.ecommerce.utils.ImplFunction;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private SalesRepo salesRepo;

    @Autowired
    private ApplicationEventPublisher eventPublisher;


    @Autowired
    private ReviewResourceRepo reviewResourceRepo;
    @Autowired
    private PurchaseHistory purchaseHistory;

    private Vendor getVendor(String email){
        return vendorRepo.findByUserEmail(email).orElseThrow(()-> new RuntimeException("vendor not found"));
    }

    @Override
    @Transactional
    public String saveProduct(productsDto request,resourceDetailsTdo images,String email) {
        var vendor = getVendor(email);
//        var extraAttributes = ProductExtraAttributes.builder()
//                .extraAttributes(extraAttributess)
//                .productId(null)
//                .build();
//        String newAttributeId = productExtraAttributesRepo.save(extraAttributes).getId();
        List<Tags> newTags = new ArrayList<>();
        for(String tag: request.getTags()){
            var tag1 = tagsRepo.findByName(tag);
            log.info(tag1.getName());
             newTags.add(tag1);
        }
        WareHouse wareHouse = wareHouseRepo.findByIdentifier(request.getWareHouseId()).orElseThrow(()->{throw new RuntimeException("No warehouse exists");});
        var product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .stock(request.getStock())
                .vendor(vendor)
                .averageRating(0.0)
                .brand(request.getBrand())
                .wareHouse(wareHouse)
                .reviews(new ArrayList<>())
//                .extraAttributesId(newAttributeId)
                .regularPrice(request.getRegularPrice())
                .tags(newTags)
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
        wareHouse.setCapacity(wareHouse.getCapacity() - inventoryProduct.getStock());
        createProductSales(product);
        var meta = MetaAttributes.builder()
                .product(product1)
                .attributes(MetaAttribute.NEW_PRODUCT)
                .isActive(true)
                .build();

        try {
            productsRepo.save(product1);
            inventoryRepo.save(inventoryProduct);
            wareHouseRepo.save(wareHouse);
            specialAttributesRepo.save(meta);
            return product.getId().toString();
        } catch (Exception e) {
            log.error(e.getMessage());
            return "error";
        }
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

    public Review saveReviewImages(List<MultipartFile> images, Review review) {
        boolean success = true; // Initialize a boolean variable to track success
        List<ReviewImageResource> ResourceDetails = new ArrayList<>();
        for (MultipartFile image : images) {
            try {
                HashMap response = SaveImageTOFs(image);
                if (response.get("originalFileName") != null && response.get("destination") != null) {

                    var resource = ReviewImageResource.builder()
                            .name(response.get("originalFileName").toString())
                            .url(response.get("newFileName").toString())
                            .review(review)
                            .build();

                    reviewResourceRepo.save(resource);
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
        review.setImages(ResourceDetails);


        if (success) {
            log.info("success");
            return review;
        }
        else {
            log.info("error");
            return null;
        }
    }

    private boolean deleteFiles(Product product) {
        var ResourceDetails = resourceRepo.findByProductId(product.getId()).orElseThrow(()-> new RuntimeException("image not found"));
        List<String> fileNames = ResourceDetails.stream().map(resourceDetails::getUrl).toList();
        boolean success = true;
        String directoryPath = "C:/Users/tonys/Downloads/product-Images/";

        for (String fileName : fileNames) {
            File file = new File(directoryPath + fileName);

            if (file.exists()) {
                if (file.delete()) {
                    System.out.println(fileName + " deleted successfully.");
                } else {
                    System.out.println("Failed to delete " + fileName);
                    success = false;
                }
            } else {
                System.out.println(fileName + " does not exist.");
                success = false;
            }
        }

        return success;
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
    public List<String> searchProducts(String keyword, String category) {
        if (keyword == null || keyword.isBlank()) {
            return null;
        }
        if (category == null || category.isBlank()) {
            category = null;
        }
        List<Product> products = productsRepo.findProductsByRegex(keyword, category);
        if (!products.isEmpty()) {
            return products.stream().map(Product::getName).toList();
        } else {
            return Collections.emptyList();
        }
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
    public List<ProductResponse> searchResults(String keyword, String category, Pageable pageable) {
        if(keyword == null || keyword.isBlank()) {
            return null;
        }
        if(category == null || category.isBlank()) {
            category = null;
        }
        List<ProductResponse> productsList = new ArrayList<>();

        Page<Product> products = productsRepo.findProductsByRegexPageable(keyword,category,pageable);
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
                productsList.add(response);
        });


        return productsList;
    }


    @Override
    @Transactional
    public String addReview(ReviewRequest request, List<MultipartFile> images) {
        var product = productsRepo.findByIdentifier(request.getProductId()).orElseThrow(()-> new RuntimeException("product not found"));
        var customer = customerServices.getCustomer(request.getEmail());
        var histroy = purchaseHistory.findByIdentifier(request.getPurchaseId());
        Review review = null;
        if(reviewsRepo.findByUserPurchaseHistoryId(histroy.getId()) == null) {
            review = Review.builder()
                    .product(product)
                    .rating(request.getRating())
                    .headline(request.getHeadline())
                    .review(request.getReview())
                    .userPurchaseHistory(histroy)
                    .customer(customer)
                    .date(new Date())
                    .build();
            review = saveReviewImages(images, review);
            product.getReviews().add(review);
            reviewsRepo.save(review);
            productsRepo.save(product);
            updateProductRating(product);
            return "success";
        }
        return "REVIEW_EXIST";
    }

    private void updateProductRating(Product product){
        var review  = reviewsRepo.findAllByProduct(product);
        var averageRating = review.stream().mapToDouble(review1-> review1.getRating()).average().orElse(0.0);
        product.setAverageRating(averageRating);
    }



    @Override
    public List<ReviewRequest> getAllProductReviews(String productId) {
        var reviews = reviewsRepo.findAllByProductIdentifier(productId);
        var response = new ArrayList<ReviewRequest>();
        reviews.stream().sorted(Comparator.comparing(Review::getDate).reversed()).forEach(review -> {
            var reviewRequest = ReviewRequest.builder()
                    .firstName(review.getCustomer().getUser().getFirstName())
                    .lastName(review.getCustomer().getUser().getLastName())
                    .date(review.getDate())
                    .ProfileIconId(review.getCustomer().getUser().getImageId())
                    .rating(review.getRating())
                    .review(review.getReview())
                    .headline(review.getHeadline())
                    .build();
            response.add(reviewRequest);
        });
        return response;
    }

    @Override
    public List<ProductResponse> getProductAttributes(MetaAttribute attribute) {
        log.info( "{}",attribute);
        List<MetaAttributes> products = specialAttributesRepo.findAllProductByAttributesAndIsActive(attribute,true);
        List<ProductResponse> productsList = new ArrayList<>();
        products.forEach(product -> {
            if(!product.getProduct().isEnabled()){
                return;
            }
            var response = ProductResponse.builder()
                    .name(product.getProduct().getName())
                    .description(product.getProduct().getDescription())
                    .category(product.getProduct().getCategory())
                    .regularPrice(product.getProduct().getRegularPrice())
                    .imageURL(parseImageURL(product.getProduct().getImages()))
                    .productId(product.getProduct().getIdentifier())
                    .stock(product.getProduct().getStock())
                    .isPublished(product.getProduct().isEnabled())
                    .numberOfRatings(product.getProduct().getReviews().size())
                    .rating(product.getProduct().getAverageRating())
                    .build();
            if(product.getProduct().getSalePrice() != 0){
                response.setSalePrice(product.getProduct().getSalePrice());
            }
            productsList.add(response);
        });

        return productsList.stream()
                .sorted(Comparator.comparingDouble(ProductResponse::getRating).reversed())
                .collect(Collectors.toList());
    }


    @Override
    public String updateProduct(productsDto request, String productId, List<MultipartFile> images) {
        var product = productsRepo.findByIdentifier(productId).orElseThrow(()-> new RuntimeException("product not found"));
        List<Tags> newTags = new ArrayList<>();
        for(String tag: request.getTags()){
            newTags.add(tagsRepo.findByName(tag));
        }
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCategory(request.getCategory());
        product.setRegularPrice(request.getRegularPrice());
        product.setBrand(request.getBrand());
        product.setStock(request.getStock());
        product.setTags(newTags);
        product.setSalePrice(request.getSalePrice() !=0?request.getSalePrice():0);
        product.setImages(new ArrayList<>());
        product.setThumbnail(null);
        Product product1 = saveImages(resourceDetailsTdo.builder().image(images).build(),product);
        try {
            productsRepo.save(product1);
            return "success";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "error";
        }



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
            cartItemsRepo.findAllByProductIdentifier(productId).ifPresent(items -> {
                items.forEach(item -> {
                    Customer customer = item.getCart().getCustomer();
                    userEntity user = customer.getUser();
                    eventPublisher.publishEvent(new MessageEmitter(item.getProduct().getName()+ " is no longer available And hasBeen Moved To WishList" +
                            "You will Be Notified If Its Available Again", MessageAction.PRODUCT_REMOVED, MessageStatus.UNSEEN, user));

                });
            });
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
            userEntity user = customer.getUser();
            log.info("item deleted");
            Cart cart = customer.getCart();
            WishList wishList = customer.getWishList();
            wishList.getProduct().add(item.getProduct());
            cart.getItems().remove(item);
            cartItemsRepo.delete(item);
            cartRepo.save(cart);
            wishListRepo.save(wishList);
            eventPublisher.publishEvent(new MessageEmitter(item.getProduct().getName()+ " is no longer available And hasBeen Moved To <a href='/user/wishlist'><span> WishList </span></a>"  +
                    "You will Be Notified If Its Available Again", MessageAction.PRODUCT_REMOVED, MessageStatus.UNSEEN, user));

        }
        return "success";
    }

    public productsDto getEditProductDetails(String productId){
        var product = productsRepo.findByIdentifier(productId).orElseThrow(()-> new RuntimeException("product not found"));
        var attributes = specialAttributesRepo.findByProductId(product.getId());
        var tags = product.getTags().stream().map(Tags::getName).toList();
        var extraAttributes = productExtraAttributesRepo.findById(product.getExtraAttributesId()).orElseThrow(()-> new RuntimeException("product not found"));
        return productsDto.builder()
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .regularPrice(product.getRegularPrice())
                .brand(product.getBrand())
                .stock(product.getStock())
                .tags(tags)
                .salePrice(product.getSalePrice())
//                .images(FileManagerproduct.getImages().stream().map(resourceDetails::getUrl).toList())
//                .extraAttributes(extraAttributes.getExtraAttributes())
                .build();
    }

    public String createProductSales(Product product){

        SalesData salesData = SalesData.builder()
                .product(product)
                .vendor(product.getVendor())
                .revenue(0)
                .salesHistory(new ArrayList<>())
                .build();
        salesRepo.save(salesData);
        return "success";
    }


    @Override
    public ProductResponse getProduct(String productId) {
        var product = productsRepo.findByIdentifier(productId).orElseThrow(()-> new RuntimeException("product not found"));


        var response = ProductResponse.builder()
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .regularPrice(product.getRegularPrice())
                .imageURL(parseImageURL(product.getImages()))
                .brand(product.getBrand())
                .vendorName(product.getVendor().getVendorCompanyName())
                .productId(product.getIdentifier())
                .numberOfRatings(product.getReviews().size())
                .rating(product.getAverageRating())
                .stock(product.getStock())
                .isPublished(product.isEnabled())
                .salePrice(product.getSalePrice())
                .build();
        if(product.getSalePrice() != 0){
            response.setSalePrice(product.getSalePrice());
        }
        return response;
    }

    @Override
    public List<MetaAttribute> getProductAttributesList() {

        return Arrays.asList(MetaAttribute.values());
    }

    public List<ProductMinorDetails> getVendorProducts(String email){
        var vendor = vendorRepo.findByUserEmail(email).orElseThrow(()-> new RuntimeException("vendor not found"));
        List<ProductMinorDetails> products = new ArrayList<>();
        productsRepo.findAllByVendorId(vendor.getId()).forEach(product -> {
            var response = ProductMinorDetails.builder()
                    .productId(product.getIdentifier())
                    .name(product.getName())
//                    .imageURL(parseImageURL(product.getImages().get(0)))
                    .regularPrice(product.getRegularPrice())
                    .salePrice(product.getSalePrice())
                    .innDate(product.getProductInventory().getInDate())
                    .stock(product.getStock())
                    .build();
            products.add(response);
        });
        return products;

    }

    public ProductMajorDetails getProductMajorDetails(String productId){


           var product = productsRepo.findByIdentifier(productId).orElseThrow(()-> new RuntimeException("product not found"));

            var attributes = specialAttributesRepo.findByProductId(product.getId());
            var inventory = inventoryRepo.findByProductIdentifier(productId).orElseThrow(()-> new RuntimeException("inventory notFound"));
        var salesData = salesRepo.findByProductIdentifier(product.getIdentifier());
        return ProductMajorDetails.builder()
                .productId(product.getIdentifier())
                .name(product.getName())
                .imageURL(parseImageURL(product.getImages()))
                .regularPrice(product.getRegularPrice())
                .salePrice(product.getSalePrice())
                .rating(product.getAverageRating())
                .numberOfRatings(product.getReviews().size())
                .isPublished(product.isEnabled())
                .stock(inventory.getStock())
                .metaAttribute( attributes ==null ? MetaAttribute.NOT_SPONSORED : attributes.getAttributes())
                .revenue(salesData.getRevenue())
                .productSold(salesData.getProductSold())
                .innDate(product.getProductInventory().getInDate())
                .build();
    }



    @Override
    @Transactional
    public String updateProductMajorDetails(ProductRequest request, String productId) {
        var product = productsRepo.findByIdentifier(productId).orElseThrow(()-> new RuntimeException("product not found"));
        MetaAttributes attributes = specialAttributesRepo.findByProductId(product.getId());
        var inventory = inventoryRepo.findByProductIdentifier(productId).orElseThrow(()-> new RuntimeException("inventory not found"));
        log.info("{}",request.getAttributes());
        if(attributes == null){
            attributes = MetaAttributes.builder()
                    .product(product)
                    .attributes(request.getAttributes())
                    .isActive(true)
                    .build();
        }else {
            attributes.setAttributes(request.getAttributes());
        }

        product.setRegularPrice(request.getRegularPrice());
        product.setSalePrice(request.getSalePrice() != 0 ? request.getSalePrice() : 0);
        product.setStock(request.getQuantity());
        inventory.setStock(request.getQuantity());
        inventoryRepo.save(inventory);
        specialAttributesRepo.save(attributes);
        productsRepo.save(product);
        return "success";
    }

    @Override
    public DetailedProductRating getProductDetailReview(String productId) {
        List<Review> reviews  = reviewsRepo.findAllByProductIdentifier(productId);
        return DetailedProductRating.builder()
                .ratingData(processRatingData(reviews))
                .averageRating(reviews.stream().mapToDouble(Review::getRating).average().orElse(0))
                .totalRating(reviews.size())
                .productId(productId)
                .build();
    }

    private Map<String,Integer> processRatingData(List<Review> reviews) {
        Map<String, Integer> ratingData = new HashMap<>(){{
            put("1", 0);
            put("2", 0);
            put("3", 0);
            put("4", 0);
            put("5", 0);
        }};

        for(Review review: reviews) {
            int rating = review.getRating();
            ratingData.put(String.valueOf(rating), ratingData.get(String.valueOf(rating)) + 1);
        }
        return ratingData;
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


    public List<ProductViewDto> getProductsByCategory(String category) {
        List<Product> products = productsRepo.findAllByCategory(category);
        List<ProductViewDto> response = new ArrayList<>();
        products.forEach(product->{
            var res = ProductViewDto.builder()
                    .name(product.getName())
                    .productId(product.getIdentifier())
                    .imageURL(ImplFunction.parseImageURL(product.getImages()))
                    .regularPrice(product.getRegularPrice())
                    .rating(product.getAverageRating())
                    .published(product.isEnabled())
                    .stock(product.getStock())
                    .thumbnail(parseImageURL(product.getImages()).get(0))
                    .category(product.getCategory())
                    .build();
            if(product.getSalePrice() != 0){
                res.setSalePrice(product.getSalePrice());
            }

            response.add(res);
        });
        return response;
    }
}
