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
import com.Onestop.ecommerce.utils.ImageService;
import com.Onestop.ecommerce.utils.ImplFunction;
import com.Onestop.ecommerce.utils.JsonUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.json.JSONException;
import org.json.JSONObject;
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

    private final ImageService imageService = new ImageService();



    private Vendor getVendor(String email){
        return vendorRepo.findByUserEmail(email).orElseThrow(()-> new RuntimeException("vendor not found"));
    }

    @Override
    @Transactional
    public String saveProduct(productsDto request,resourceDetailsTdo images,String email,MultipartFile thumbnailFile) {
        var vendor = getVendor(email);
        ProductExtraAttributes extraAttributes = null;
        try {
        if(!request.getExtraAttributes().isEmpty()) {
            JSONObject jsonObject = new JSONObject(request.getExtraAttributes());
            extraAttributes = ProductExtraAttributes.builder()
                    .extraAttributes(JsonUtils.jsonToMap(jsonObject))
                    .productId(null)
                    .build();
        }
        }catch (JSONException e){
            e.printStackTrace();
        }

        assert extraAttributes != null;
        String newAttributeId = productExtraAttributesRepo.save(extraAttributes).getId();
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
                .averageRating(0)
                .brand(request.getBrand())
                .wareHouse(wareHouse)
                .images(new ArrayList<>())
                .reviews(new ArrayList<>())
                .extraAttributesId(newAttributeId)
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
        Product product1 = saveImages(images,product,thumbnailFile);
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
            return "SUCCESS";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "error";
        }
    }





    @Override
    public Product saveImages(resourceDetailsTdo images, Product product, MultipartFile thumbnailFile) {

        List<resourceDetails> ResourceDetails = new ArrayList<>();
        for (MultipartFile image : images.getImage()) {
            try {
                ResourceInfo response = SaveImageTOFs(image);
                if(response == null){
                    return null;
                }
                    var resource = resourceDetails.builder()
                            .name(response.getOriginalFileName())
                            .url(response.getNewFileName())
                            .downSizedUrl(response.getDownSizedFileName())
                            .product(product)
                            .build();

                    resourceRepo.save(resource);
                    ResourceDetails.add(resource);


            } catch (IOException e) {
                log.error("error from image");
                e.printStackTrace();
                return null; // Set success to false in case of an exception

            } catch (ImageWriteException | ImageReadException e) {
                throw new RuntimeException(e);
            }
        }
       product.getImages().addAll(ResourceDetails);

        if(thumbnailFile != null){
            try {
                ResourceInfo response = SaveImageTOFs(thumbnailFile);
                if(response == null){
                    return null;
                }
                var resource = resourceDetails.builder()
                        .name(response.getOriginalFileName())
                        .url(response.getNewFileName())
                        .downSizedUrl(response.getDownSizedFileName())
                        .product(product)
                        .build();

                resourceRepo.save(resource);
                ResourceDetails.add(resource);
                product.setThumbnail(resource);


            } catch (IOException e) {
                log.error("error from thumbnail");
                e.printStackTrace();
                return null; // Set success to false in case of an exception

            } catch (ImageWriteException | ImageReadException e) {
                throw new RuntimeException(e);
            }
        }
        return product;
    }

    public Review saveReviewImages(List<MultipartFile> images, Review review) {
        boolean success = true; // Initialize a boolean variable to track success
        List<ReviewImageResource> ResourceDetails = new ArrayList<>();
        for (MultipartFile image : images) {
            try {
                ResourceInfo response = SaveImageTOFs(image);
                if(response == null){
                    return null;
                }
                var resource = ReviewImageResource.builder()
                                .name(response.getOriginalFileName())
                                        .url(response.getNewFileName())
                                                .downSizedUrl(response.getDownSizedFileName())
                                                        .review(review)
                                                                .build();

                reviewResourceRepo.save(resource);
                ResourceDetails.add(resource);


            } catch (IOException | ImageWriteException | ImageReadException e) {
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

    private <T> void deleteFiles(T fileName) {

        boolean success = true;
        String directoryPath = "C:/Users/tonys/Downloads/product-Images/";
        File file = new File(directoryPath + fileName);

// Check if the file is in the correct directory
        if (file.getParent().equals(directoryPath)) {
            System.out.println("File Directory: " + file.getParent());  // Print the directory
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
        } else {
            System.out.println(fileName + " is not in the correct directory.");
            success = false;
        }


    }



    @Override
    @Deprecated
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
                    .imageURL(ImplFunction.pareImageURLToMap(product.getImages()))
                    .productId(product.getIdentifier())
                    .numberOfRatings(product.getReviews().size())
                    .rating(product.getAverageRating())
                    .vendorName(product.getVendor().getVendorCompanyName())
                    .stock(product.getStock())
                    .brand(product.getBrand())
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
            return products.stream()
                    .sorted(Comparator.comparing(Product::getAverageRating).reversed())
                    .limit(6)
                    .map(Product::getName)
                    .collect(Collectors.toList());
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
    public List<ProductResponse> searchResults(String keyword, String category, Pageable pageable, Double averageRating, Double[] range) {
        if(keyword == null || keyword.isBlank()) {
            keyword = null;
        }
        if(category == null || category.isBlank()) {
            category = null;
        }
        List<ProductResponse> productsList = new ArrayList<>();
        List<String> categories = category != null
                ? Arrays.stream(category.split(","))
                .map(String::trim) // Trim leading and trailing spaces
                .collect(Collectors.toList())
                : new ArrayList<>();

//        range = range != null ? range : new Double[]{0.0, Double.MAX_VALUE};


        String finalKeyword = keyword;
        Double[] finalRange = range;

        Page<Product> products = productsRepo.searchProducts(finalKeyword, categories, averageRating, pageable);
        products.forEach(product -> {
            if (!product.isEnabled()) {
                return;
            }
            var response = ProductResponse.builder()
                    .name(product.getName())
                    .description(product.getDescription())
                    .category(product.getCategory())
                    .regularPrice(product.getRegularPrice())
                    .imageURL(ImplFunction.pareImageURLToMap(product.getImages()))
                    .productId(product.getIdentifier())
                    .thumbnail(product.getThumbnail() !=null ?ImplFunction.pareImageURLToMap(product.getThumbnail()):null)
                    .numberOfRatings(product.getReviews().size())
                    .brand(product.getBrand())
                    .vendorName(product.getVendor().getVendorCompanyName())
                    .stock(product.getStock())
                    .rating(product.getAverageRating())
                    .build();
            if (product.getSalePrice() != 0) {
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
                    .images(ImplFunction.parseImageURLs(reviewResourceRepo.findAllByReviewId(review.getId())))
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
                    .brand(product.getProduct().getBrand())
                    .vendorName(product.getProduct().getVendor().getVendorCompanyName())
                    .regularPrice(product.getProduct().getRegularPrice())
                    .imageURL(ImplFunction.pareImageURLToMap(product.getProduct().getImages()))
                    .productId(product.getProduct().getIdentifier())
                    .stock(product.getProduct().getStock())
                    .isPublished(product.getProduct().isEnabled())
                    .numberOfRatings(product.getProduct().getReviews().size())
                    .rating(product.getProduct().getAverageRating())
                    .imagePreview(product.getProduct().getThumbnail() !=null ?ImplFunction.parsePreviewImageURL(product.getProduct().getThumbnail()):ImplFunction.parsePreviewImageURL(product.getProduct().getImages().get(0)))
                    .thumbnail(product.getProduct().getThumbnail() !=null ?ImplFunction.pareImageURLToMap(product.getProduct().getThumbnail()):null)
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
    @Transactional
    public String updateProduct(productsDto request, String productId, List<MultipartFile> images, MultipartFile thumbnailFile) {
        Product product = productsRepo.findByIdentifier(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        var inventory = inventoryRepo.findByProductIdentifier(productId)
                .orElseThrow(() -> new RuntimeException("inventory not found"));

        product.setThumbnail(null);
        var existingImages = resourceRepo.findByProductId(product.getId());
        if(product.getExtraAttributesId() == null){
            JSONObject object = new JSONObject(request.getExtraAttributes());
            ProductExtraAttributes extraAttributes = ProductExtraAttributes.builder()
                    .extraAttributes(JsonUtils.jsonToMap(object))
                    .productId(product.getIdentifier())
                    .build();
            productExtraAttributesRepo.save(extraAttributes);
            product.setExtraAttributesId(extraAttributes.getId());
        }else{

            var extraAttributes = productExtraAttributesRepo.findById(product.getExtraAttributesId());
            if(extraAttributes.isPresent()){
                JSONObject object = new JSONObject(request.getExtraAttributes());
                extraAttributes.get().setExtraAttributes(JsonUtils.jsonToMap(object));
                productExtraAttributesRepo.save(extraAttributes.get());
            }
        }


        // Remove images that are not in the request
        List<String> productNames = request.getExistingImages().stream()
                .map(url -> url.substring(url.lastIndexOf('/') + 1))
                .toList();

        productNames.forEach(image -> {
            log.info("{}",image);
        });

        List<resourceDetails> imagesToRemove = null;

            imagesToRemove = existingImages.map(imagess ->

                            imagess.stream()
                                    .filter(image -> !productNames.contains(image.getUrl()))
                                    .toList())
                    .orElse(Collections.emptyList());

        imagesToRemove.forEach(image -> {
            log.info("{}",image.getName());
        });

        // Update product details
        List<String> existingTags = product.getTags().stream()
                .map(Tags::getName)
                .toList();

        List<Tags> newTags = request.getTags().stream()
                .filter(tag -> !existingTags.contains(tag))
                .map(tagsRepo::findByName)
                .collect(Collectors.toList());

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCategory(request.getCategory());
        product.setRegularPrice(request.getRegularPrice());
        product.setBrand(request.getBrand());
        product.setStock(request.getStock());
        product.setThumbnail(request.getThumbnail() != null
                ? resourceRepo.findByUrl(request.getThumbnail().substring(request.getThumbnail().lastIndexOf('/') + 1))
                : null);
        product.setTags(newTags);
        product.setSalePrice(request.getSalePrice() != 0 ? request.getSalePrice() : 0);
        inventory.getWareHouse().setCapacity(inventory.getWareHouse().getCapacity() + inventory.getStock() - request.getStock());
        inventory.setStock(request.getStock());



        if (images != null) {
            product = saveImages(resourceDetailsTdo.builder().image(images).build(), product, thumbnailFile);
        }

//        log.info("{}",imagesToRemove.get(0));
        Product finalProduct = product;
        imagesToRemove.forEach(image -> {
            finalProduct.getImages().remove(image);
            productsRepo.save(finalProduct);
            resourceRepo.delete(image);
            deleteFiles(image.getUrl());
            deleteFiles(image.getDownSizedUrl());

        });
        try {
            inventoryRepo.save(inventory);
            productsRepo.save(finalProduct);
            return "SUCCESS";
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
//        var attributes = specialAttributesRepo.findByProductId(product.getId());
        var tags = product.getTags().stream().map(Tags::getName).toList();
       Optional<ProductExtraAttributes> extraAttributes  = Optional.empty();
        if(product.getExtraAttributesId() != null){
             extraAttributes = productExtraAttributesRepo.findById(product.getExtraAttributesId());
        }

        assert extraAttributes.isPresent();
        return productsDto.builder()
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .regularPrice(product.getRegularPrice())
                .brand(product.getBrand())
                .stock(product.getStock())
                .tags(tags)
                .wareHouseId(product.getWareHouse().getWareHouseName())
                .salePrice(product.getSalePrice())
                .image(product.getImages() !=null ?ImplFunction.parseImageURL(product.getImages()):null)
                .thumbnail(product.getThumbnail() !=null ?ImplFunction.parseImageURL(product.getThumbnail()):null)
                .extraObjects(extraAttributes.map(ProductExtraAttributes::getExtraAttributes).orElse(null))
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
        Optional<ProductExtraAttributes> extraAttribute = Optional.empty();
        if(product.getExtraAttributesId() != null){
            extraAttribute = productExtraAttributesRepo.findById(product.getExtraAttributesId());
        }


        assert extraAttribute != null;
        var response = ProductResponse.builder()
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .regularPrice(product.getRegularPrice())
                .imageURL(ImplFunction.pareImageURLToMap(product.getImages()))
                .brand(product.getBrand())
                .vendorName(product.getVendor().getVendorCompanyName())
                .productId(product.getIdentifier())
                .numberOfRatings(product.getReviews().size())
                .extraAttributes(extraAttribute.<Map<String, ?>>map(ProductExtraAttributes::getExtraAttributes).orElse(null))
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
                    .imageURL(product.getThumbnail() != null? ImplFunction.parseImageURL(product.getThumbnail()) : ImplFunction.parseImageURL(product.getImages().get(0)))
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
        product.setStock( request.getQuantity());
        inventory.getWareHouse().setCapacity(inventory.getWareHouse().getCapacity() + inventory.getStock() - request.getQuantity());
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
        Set<String> imageURL = new HashSet<>();
        images.forEach(image -> {
            var url = "http://localhost:8000/image-resources/product-Images/" + image.getUrl();
            imageURL.add(url);
        });
        return new ArrayList<>(imageURL);
    }



    public ResourceInfo SaveImageTOFs(MultipartFile image) throws IOException, ImageWriteException, ImageReadException {
        String userHome = System.getProperty("user.home");
        String downloadsDir = userHome + "\\Downloads\\product-Images\\";
        Path directoryPath = Path.of(downloadsDir);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        MultipartFile downSizedImage = imageService.resizeImage(image, 20);

        HashMap<String, String> originalImageInfo = saveImage(image, downloadsDir);
        HashMap<String, String> downSizedImageInfo = saveImage(downSizedImage, downloadsDir);

        if (downSizedImageInfo != null && originalImageInfo != null ) {
                return ResourceInfo.builder()
                        .originalFileName(originalImageInfo.get("originalFileName"))
                        .newFileName(originalImageInfo.get("newFileName"))
                        .downSizedFileName(downSizedImageInfo.get("newFileName"))
                        .build();

        }else {
            return null;
        }
    }

    private HashMap<String, String> saveImage(MultipartFile image, String directory) {
        try {
            String originalFileName = image.getOriginalFilename();
            String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;
            File destination = new File(directory + uniqueFileName);

            HashMap<String, String> response = new HashMap<>();
            response.put("originalFileName", originalFileName);
            response.put("destination", destination.getAbsolutePath());
            response.put("newFileName", uniqueFileName);
            image.transferTo(destination);

            // Return a success response
            return response;
        } catch (IOException e) {
            log.error("Error from image");
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
                    .imageURL(product.getImages() !=null ?ImplFunction.pareImageURLToMap(product.getImages()):null)
                    .regularPrice(product.getRegularPrice())
                    .rating(product.getAverageRating())
                    .published(product.isEnabled())
                    .stock(product.getStock())

                    .thumbnail(product.getThumbnail() !=null ?ImplFunction.pareImageURLToMap(product.getThumbnail()):null)
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
