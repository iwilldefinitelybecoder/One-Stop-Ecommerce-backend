package com.Onestop.ecommerce.Service.VendorServices;

import com.Onestop.ecommerce.Controller.vendor.VendorLoginRequest;
import com.Onestop.ecommerce.Controller.vendor.VendorLoginResponse;
import com.Onestop.ecommerce.Controller.vendor.VendorRequest;
import com.Onestop.ecommerce.Dto.CustomerDto.AddressRequest;
import com.Onestop.ecommerce.Dto.VendorDto.DashboardData;
import com.Onestop.ecommerce.Dto.VendorDto.VendorProductList;
import com.Onestop.ecommerce.Dto.productsDto.ProductResponse;
import com.Onestop.ecommerce.Entity.Logistics.WareHouse;
import com.Onestop.ecommerce.Entity.Role;
import com.Onestop.ecommerce.Entity.orders.OrderStatus;
import com.Onestop.ecommerce.Entity.products.Review;
import com.Onestop.ecommerce.Entity.vendor.SalesData;
import com.Onestop.ecommerce.Entity.vendor.SalesHistory;
import com.Onestop.ecommerce.Entity.vendor.Vendor;
import com.Onestop.ecommerce.Repository.CustomerRepo.OrdersRepo.OrderItemsRepo;
import com.Onestop.ecommerce.Repository.CustomerRepo.OrdersRepo.OrdersRepo;
import com.Onestop.ecommerce.Repository.LogisticsRepo.InventoryRepo;
import com.Onestop.ecommerce.Repository.LogisticsRepo.WareHouseRepo;
import com.Onestop.ecommerce.Repository.VendorRepo.SalesHistoryRepo;
import com.Onestop.ecommerce.Repository.VendorRepo.SalesRepo;
import com.Onestop.ecommerce.Repository.VendorRepo.VendorRepository;
import com.Onestop.ecommerce.Repository.products.ReviewsRepo;
import com.Onestop.ecommerce.Repository.products.productsRepo;
import com.Onestop.ecommerce.Repository.products.resourceRepo;
import com.Onestop.ecommerce.Repository.userRepo.RoleRepository;
import com.Onestop.ecommerce.Repository.userRepo.UserRepository;
import com.Onestop.ecommerce.utils.DateObject;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class VendorServices implements VendorService {

    private final VendorRepository vendorRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final productsRepo productsRepo;
    private final WareHouseRepo wareHouseRepo;
    private final InventoryRepo inventoryRepo;
    private final ReviewsRepo reviewsRepo;
    private final resourceRepo resourceRepo;
    private final SalesRepo salesrepo;
    private final SalesHistoryRepo salesHistoryRepo;
    private final OrderItemsRepo orderItemsRepo;



    public String register(VendorRequest request) {

        var user = userRepository.findByEmail(request.getUserEmail()).orElse(null);
        var role = roleRepository.findByName("VENDOR");
        if(user == null){
            return "NULL";
        }
        var vendor  = Vendor.builder()
                .user(user)
                .supportEmail(request.getVendorEmail())
                .vendorCompanyName(request.getVendorCompanyName())
                .build();
        log.info("Vendor: {}",vendor.getVendorCompanyName());
        vendorRepository.save(vendor);
        var addrole = user.getRoles();
        addrole.add(role);
        user.setRoles(addrole);
        userRepository.save(user);
        return "SUCCESS";


    }

    public VendorLoginResponse authenticate(String email) {
        var user  = userRepository.findByEmail(email).orElse(null);
        if(user == null){
            return null;
        }
        if(user.getRoles().stream().toList().contains("VENDOR")) {

            return VendorLoginResponse.builder()
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .imageId(user.getImageId())
                    .roles(user.getRoles())
                    .build();
        }else{
            return null;
        }
    }

    @Override
    public List<VendorProductList> getAllProducts(String userName) {
        Vendor vendor = vendorRepository.findByUserEmail(userName).orElse(null);
        if(vendor == null){
            throw new RuntimeException("Vendor not found");
        }
        var products = productsRepo.findAllByVendorId(vendor.getId());
        List<VendorProductList> vendorProductLists = new ArrayList<>();
        for (var product:products) {
            log.info("Product: {}",product.getId());
            vendorProductLists.add(VendorProductList.builder()
                    .productId(product.getIdentifier())
                    .name(product.getName())
                    .regularPrice(product.getRegularPrice())
                    .salePrice(product.getSalePrice())
                    .Stock(product.getStock())
                    .isEnabled(product.isEnabled())
                    .build());

        }
        return vendorProductLists;
    }

    private String parseImageUrl(String imageName){
        return "http://localhost:8080/image-resources/product-Images"+imageName;
    }

    @Override
    @Transactional
    public String deleteProduct(String identifier, String userName) {
       var product = productsRepo.findByIdentifier(identifier).orElse(null);
         if(product == null){
              throw new RuntimeException("Product not found");
         }
            var vendor = product.getVendor();
            var WareHouse = product.getWareHouse();
            var reviews = product.getReviews();
            var inventory = product.getProductInventory();
            var resources = product.getImages();
            if(reviews != null){
                reviewsRepo.deleteAll(reviews);
            }
            if(inventory != null){
                inventoryRepo.delete(inventory);
            }
            if(resources != null){
                resourceRepo.deleteAll(resources);
            }
            productsRepo.delete(product);
            vendor.getProduct().remove(product);
            WareHouse.getInventory().remove(inventory);
            vendorRepository.save(vendor);
            wareHouseRepo.save(WareHouse);
            return "SUCCESS";


    }

    @Override
    public String updateProduct(AddressRequest address, String identifier) {
        return null;
    }

    @Override
    public ProductResponse getProductDetails(String identifier) {
        return null;
    }


    public DashboardData getDashboardData(String productId,String userName) {
        var salesData = salesrepo.findByVendorUserEmail(userName);
        var history = salesHistoryRepo.findAllBySalesDataVendorUserEmail(userName);
        var orderItems = orderItemsRepo.findAllByVendorUserEmail(userName);
        var reviews = reviewsRepo.findAllByProductVendorUserEmail(userName);
        var dashboardDate = DashboardData.builder();
        List<DateObject> salesDate = new ArrayList<>();
        for(SalesHistory history1 : history){
            salesDate.add(new DateObject(history1.getDate(),history1.getCount()));
        }
        List<Date> pendingOrders = orderItems.stream().filter(orderItems1 -> {
            OrderStatus status = orderItems1.getStatus();
            return !(OrderStatus.DELIVERED.equals(status) || OrderStatus.CANCELLED.equals(status));
        }).map(item->item.getOrders().getOrderDate())
                .sorted(Comparator.comparing(Date::getTime))
                .toList();
        if(!pendingOrders.isEmpty()){
            dashboardDate.from(pendingOrders.get(0));
            dashboardDate.to(pendingOrders.get(pendingOrders.size() - 1));

        }
        dashboardDate.pendingOrders(pendingOrders.size());
        var earnings = salesData.stream().mapToDouble(SalesData::getRevenue).sum();
        dashboardDate.earnings(earnings);

        var vendorRating = reviews.stream().mapToDouble(Review::getRating).average().orElse(0.0);
        dashboardDate.vendorRatings(vendorRating);
        dashboardDate.salesData(salesDate);
        return dashboardDate.build();
    }
}
