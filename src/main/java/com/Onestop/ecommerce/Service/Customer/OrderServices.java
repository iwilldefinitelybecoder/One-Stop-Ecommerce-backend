package com.Onestop.ecommerce.Service.Customer;

import com.Onestop.ecommerce.Dto.CustomerDto.OrderInfo;
import com.Onestop.ecommerce.Dto.CustomerDto.Orders.*;
import com.Onestop.ecommerce.Dto.CustomerDto.ReviewDto;
import com.Onestop.ecommerce.Entity.Customer.Customer;
import com.Onestop.ecommerce.Entity.Customer.UserPurchaseHistory;
import com.Onestop.ecommerce.Entity.Customer.address.Address;
import com.Onestop.ecommerce.Entity.Customer.card.Cards;
import com.Onestop.ecommerce.Entity.Customer.cart.Cart;

import com.Onestop.ecommerce.Entity.Logistics.*;
import com.Onestop.ecommerce.Entity.Payments.PaymentLedger;
import com.Onestop.ecommerce.Entity.Payments.PaymentMethods;
import com.Onestop.ecommerce.Entity.Payments.PaymentStauts;
import com.Onestop.ecommerce.Entity.orders.CancelOrders;
import com.Onestop.ecommerce.Entity.orders.OrderItems;
import com.Onestop.ecommerce.Entity.orders.OrderStatus;
import com.Onestop.ecommerce.Entity.orders.Orders;
import com.Onestop.ecommerce.Entity.products.Product;
import com.Onestop.ecommerce.Entity.vendor.SalesData;
import com.Onestop.ecommerce.Entity.vendor.SalesHistory;
import com.Onestop.ecommerce.Repository.CustomerRepo.*;
import com.Onestop.ecommerce.Repository.CustomerRepo.OrdersRepo.CancelOrdersRepo;
import com.Onestop.ecommerce.Repository.CustomerRepo.OrdersRepo.OrderItemsRepo;
import com.Onestop.ecommerce.Repository.CustomerRepo.OrdersRepo.OrdersRepo;
import com.Onestop.ecommerce.Repository.LogisticsRepo.InventoryRepo;
import com.Onestop.ecommerce.Repository.LogisticsRepo.OrderShipment;
import com.Onestop.ecommerce.Repository.LogisticsRepo.ShipmentUpdateRepo;
import com.Onestop.ecommerce.Repository.LogisticsRepo.WareHouseRepo;
import com.Onestop.ecommerce.Repository.TransactionRepo.PaymentLedeger;
import com.Onestop.ecommerce.Repository.VendorRepo.SalesHistoryRepo;
import com.Onestop.ecommerce.Repository.VendorRepo.SalesRepo;
import com.Onestop.ecommerce.Repository.products.ReviewResourceRepo;
import com.Onestop.ecommerce.Repository.products.ReviewsRepo;
import com.Onestop.ecommerce.Repository.products.productsRepo;
import com.Onestop.ecommerce.Service.products.CouponsServices;
import com.Onestop.ecommerce.configuration.PaymentKeys;
import com.Onestop.ecommerce.utils.ImplFunction;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServices implements OrdersService {


    ImplFunction implFunction = new ImplFunction();
    private final PaymentKeys paymentKeys;

    private final AddressRepo addressRepo;
    private final CardsRepo cardsRepo;

    private final OrdersRepo ordersRepo;
    private final productsRepo productRepo;
    private final WareHouseRepo wareHouseRepo;
    private final CustomerRepo customerRepo;
    private final OrderItemsRepo orderItemsRepo;
    private final CancelOrdersRepo cancelOrdersRepo;
    private final CustomerServices customerServices;
    private final CartServices cartServices;

    private final PurchaseHistory purchaseHistory;
    private final SalesHistoryRepo salesHistoryRepo;
    private final SalesRepo salesRepo;
    private final PaymentLedeger paymentLedger;

    private final CouponsServices couponsServices;

    private final OrderShipment orderShipment;
    private final ShipmentUpdateRepo shipmentUpdateRepo;
    private final ReviewsRepo reviewsRepo;
    private final ReviewResourceRepo reviewResourceRepo;
    private final InventoryRepo inventoryRepo;




    @Override
    @Transactional
    public String createOrder(OrderRequest orderRequest) {
        log.info("order request: {}", orderRequest);
        Customer customer = customerServices.getCustomer(orderRequest.getCustomerId());
        boolean paymentStatus = false;
        if(orderRequest.getCouponId() != null){
            applyCoupon(orderRequest);
        }



        Address shippingAddress = addressRepo.findByIdentifier(orderRequest.getShippingAddressId()).orElseThrow(() -> new RuntimeException("address not found"));
        Address billingAddress = addressRepo.findByIdentifier(orderRequest.getBillingAddressId()).orElseThrow(() -> new RuntimeException("address not found"));
        Cards card = cardsRepo.findByIdentifier(orderRequest.getCardId()).orElseThrow(() -> new RuntimeException("card not found"));
        List<OrderItems> orderItems = new ArrayList<>();
        if(orderRequest.isBuyNow()){


                Product product = productRepo.findByIdentifier(orderRequest.getProducts().getProductId()).orElseThrow(() -> new RuntimeException("product not found"));

                var orderItem = OrderItems.builder()
                        .product(product)
                        .quantity(orderRequest.getProducts().getQuantity())
                        .itemTotal(orderRequest.getProducts().getQuantity() * salePriceOrRegularPrice(product))
                        .itemPrice(salePriceOrRegularPrice(product))
                        .status(OrderStatus.ORDERED)
                        .vendor(product.getVendor())
                        .build();
                orderItems.add(orderItem);
            product.setStock(product.getStock() - orderRequest.getProducts().getQuantity());
            productRepo.save((product));
            if(orderRequest.getPaymentMethod().equals(PaymentMethods.DEBITCARD)) {

                log.info("card: {}", orderRequest.isBuyNow());
                paymentStatus = completePayment(orderRequest, customer, orderRequest.getProducts().getQuantity() * salePriceOrRegularPrice(product));
            }
        }else {
            var customer1 = customerServices.getCustomer(orderRequest.getCustomerId());
            Cart cart = customer1.getCart();
            cart.getItems().forEach(item -> {
                Product product = productRepo.findByIdentifier(item.getProduct().getIdentifier()).orElseThrow(() -> new RuntimeException("product not found"));
                var orderItem = OrderItems.builder()
                        .product(product)
                        .quantity(item.getQuantity())
                        .itemTotal(item.getQuantity() * product.getSalePrice())
                        .itemPrice(product.getSalePrice())
                        .status(OrderStatus.ORDERED)
                        .vendor(product.getVendor())
                        .build();
                orderItems.add(orderItem);
                product.setStock(product.getStock() - item.getQuantity());
                productRepo.save((product));
            });
            if(orderRequest.getPaymentMethod().equals(PaymentMethods.DEBITCARD)) {

                paymentStatus = completePayment(orderRequest, customer, cart.getGrandTotal());
            }
        }
        List<List<OrderItems>> orderItemsList = batchOrders(orderItems);


        boolean finalPaymentStatus = paymentStatus;
        orderItemsList.forEach(orderItems1 ->   {
            var wareHouse = wareHouseRepo.findByIdentifier(orderItems1.get(0).getProduct().getWareHouse().getIdentifier()).orElseThrow(() -> new RuntimeException("warehouse not found"));
            var order = Orders.builder()
                    .orderDate(new Date())
                    .orderItems(orderItems1)
                    .billingAddress(billingAddress)
                    .shippingAddress(shippingAddress)
                    .paymentCard(card)
                    .generatedOrderId(ImplFunction.generateOrderId())
                    .customer(customer)
                    .total(orderTotal(orderItems1))
                    .paymentStatus(finalPaymentStatus)
                    .status(OrderStatus.ORDERED)
                    .paymentMethod(orderRequest.getPaymentMethod())
                    .wareHouse(wareHouse)
                    .build();
            orderItems1.forEach(items-> {
                        createHistory(items.getProduct(), customer, items.getQuantity(),order);
                        items.setOrders(order);
                        orderItemsRepo.save(items);
                    });
            ordersRepo.save(order);
            customer.getOrders().add(order);
            wareHouse.getOrders().add(order);
            wareHouseRepo.save(wareHouse);
            customerRepo.save(customer);
            handelShipment(order,wareHouse);
        });
        updateInventory(orderItems);
        cartServices.emptyCart(customer.getUser().getEmail());

        return "ORDER_PLACED";


    }

    private void handelShipment(Orders order, WareHouse wareHouse){
        var shipment = orderShipment.findByOrdersId(order.getId());
        if(shipment == null){
           var  shipment1 = OrderShippment.builder()
                    .carrier("Ones top")
                    .date(new Date())
                    .shippingMethod(ShipmentMethod.STANDARD)
                    .trackingNumber(ImplFunction.generateTrackingNumber())
                    .orders(order)
                    .build();

            var shipmentUpdate = ShipmentUpdates.builder()
                .date(new Date())
                .orderShippment(shipment1)
                .Status(OrderStatus.ORDERED)
                .action(ShipmentAction.PACKING)
                .date(new Date())
                .location(wareHouse.getWareHouseName())
                .orders(order)
                .build();
            shipment1.getShipmentUpdates().add(shipmentUpdate);
            orderShipment.save(shipment1);
            shipmentUpdateRepo.save(shipmentUpdate);
        }
    }

    private void applyCoupon(OrderRequest request){
        couponsServices.applyCoupon(request.getCouponId(), request.getCustomerId());
    }

    private String createHistory(Product product, Customer customer, int quantity,Orders orders){
        var inventory = inventoryRepo.findByProductIdentifier(product.getIdentifier());
        SalesData salesData = salesRepo.findByProductIdentifier(product.getIdentifier());
        UserPurchaseHistory userPurchaseHistory = UserPurchaseHistory.builder()
                .customer(customer)
                .orders(orders)
                .product(product)
                .purchaseDate(new Date())
                .quantity(quantity)
                .total(quantity * salePriceOrRegularPrice(product))
                .price(salePriceOrRegularPrice(product))
                .build();
        purchaseHistory.save(userPurchaseHistory);
       SalesHistory history = SalesHistory.builder()
               .date(new Date())
               .salesData(salesData)
               .Count(quantity)
               .saleTotal(quantity * salePriceOrRegularPrice(product))
               .build();
       salesData.setRevenue(salesData.getRevenue() + history.getSaleTotal());
       salesData.setProductSold(salesData.getProductSold() + history.getCount());
       salesData.getSalesHistory().add(history);
          salesHistoryRepo.save(history);
            salesRepo.save(salesData);
            inventory.ifPresent(productInventory -> {
                productInventory.setStock(productInventory.getStock() - quantity);
                inventoryRepo.save(productInventory);
            });

            return "SALES_HISTORY_CREATED";
    }

    private double orderTotal(List<OrderItems> orderItems) {
        return orderItems.stream().mapToDouble(OrderItems::getItemTotal).sum();
    }

    private boolean completePayment(OrderRequest orderRequest,Customer customer, double amount) {
        Stripe.apiKey = paymentKeys.getStripePrivateKey();
        if(orderRequest.isUseWallet()){
            amount = amount - customer.getWallet();
            if(customer.getWallet() >= 0){
                customer.setWallet(customer.getWallet() - amount);
                customerRepo.save(customer);
                return true;
            }
        }
        PaymentLedger paymentLedger = PaymentLedger.builder()
                .amount(amount)
                .transactionDate(new Date())
                .customer(customer)
                .paymentMethod(orderRequest.getPaymentMethod())
                .description("payment for order")
                .build();
        try {
            Charge charge = Charge.create(
                    new ChargeCreateParams.Builder()
                            .setAmount((long) (amount * 100))
                            .setCurrency("inr")
                            .setSource(orderRequest.getPaymentProcessId())
                            .setSource(orderRequest.getPaymentProcessId())
                            .build()
            );
            paymentLedger.setStatus(PaymentStauts.SUCCESS);
            paymentLedger.setTransactionId(charge.getId());
            this.paymentLedger.save(paymentLedger);
            return charge.getPaid();
        } catch (Exception e) {
            paymentLedger.setStatus(PaymentStauts.FAILED);
            this.paymentLedger.save(paymentLedger);
            log.error("error while processing payment", e);
            return false;
        }

    }

    private double salePriceOrRegularPrice(Product product){
        if(product.getSalePrice() > 0){
            return product.getSalePrice();
        }else{
            return product.getRegularPrice();
        }
    }

    private List<List<OrderItems>> batchOrders(List<OrderItems> orderItems) {
        Map<String, List<OrderItems>> segregatedOrders = new HashMap<>();
        for (OrderItems orderItem : orderItems) {
            String wareHouseId = orderItem.getProduct().getWareHouse().getIdentifier();
            segregatedOrders.computeIfAbsent(wareHouseId, k -> new ArrayList<>()).add(orderItem);
        }
        return new ArrayList<>(segregatedOrders.values());
    }

    private void updateInventory(List<OrderItems> orderItems) {
        orderItems.forEach(orderItem -> {
            var product = orderItem.getProduct();
            var inventory = product.getWareHouse().getInventory();
            inventory.forEach(productInventory -> {
                if (productInventory.getProduct().equals(product)) {
                    productInventory.setStock(productInventory.getStock() - orderItem.getQuantity());
                    inventoryRepo.save(productInventory);
                }
            });

        });
    }


    @Override
    public OrderDetailResponse getOrder(String orderId) {
        Orders order = ordersRepo.findByIdentifier(orderId).orElseThrow(() -> new RuntimeException("order not found"));
        return generateOrderResponse(order);

    }

    private OrderDetailResponse generateOrderResponse(Orders order) {
        List<ProductListOfOrder> productListOfOrders = new ArrayList<>();
        order.getOrderItems().forEach(orderItems -> {
            var purchaseData = purchaseHistory.findByCustomerIdAndProductIdAndOrdersId(order.getCustomer().getId(),orderItems.getProduct().getId(),order.getId());
            ProductListOfOrder productListOfOrder = ProductListOfOrder.builder()
                    .productId(orderItems.getProduct().getIdentifier())
                    .quantity(orderItems.getQuantity())
                    .price(orderItems.getItemPrice())
                    .totalPrice(orderItems.getItemTotal())
                    .name(orderItems.getProduct().getName())
                    .purchaseId(purchaseData != null? purchaseData.getIdentifier():null)
                    .orderItemId(orderItems.getIdentifier())
                    .status(orderItems.getStatus())
                    .build();
            productListOfOrders.add(productListOfOrder);
        });
        var summary = OrderSummary.builder()
                .itemsTotal(order.getTotal())
                .shippingTotal(order.getShippingTotal())
                .taxTotal(order.getTaxTotal())
                .grandTotal(order.getGrandTotal())
                .build();

        return OrderDetailResponse.builder()
                .orderStatus(order.getStatus())
                .BillingAddressId(order.getBillingAddress().getIdentifier())
                .ShippingAddressId(order.getShippingAddress().getIdentifier())
                .deliveryDate(order.getDeliveredDate())
                .paymentId(order.getPaymentCard().getIdentifier())
                .orderId(order.getIdentifier())
                .orderDate(order.getOrderDate())
                .paymentMethod(order.getPaymentMethod())
                .productId(productListOfOrders)
                .paymentStatus(order.isPaymentStatus())
                .TrackingId(order.getTrackingId())
                .ExpectedDeliveryDate(order.getExpectedDeliveryDate())
                .generatedOrderId(order.getGeneratedOrderId())
                .TrackingId(order.getTrackingId())
                .orderSummary(summary)
                .build();
    }

    @Override
    public String cancelOrder(OrderCancelRequest request) {
        Orders order = ordersRepo.findByIdentifier(request.getOrderId()).orElseThrow(() -> new RuntimeException("order not found"));
        OrderItems item = orderItemsRepo.findByIdentifier(request.getOrderItemId()).orElseThrow(() -> new RuntimeException("order item not found"));
        order.setStatus(OrderStatus.CANCELLED);
        item.setStatus(OrderStatus.CANCELLED);
        cancelOrdersRepo.save(CancelOrders.builder()
                .orderItems(item)
                .orders(order)
                .description(request.getComment())
                .reason(request.getReason())
                .status(OrderStatus.CANCELLED)
                .build());
        ordersRepo.save(order);
        orderItemsRepo.save(item);

        return "ORDER_CANCELLED";

    }

    @Override
    public List<OrderDetailResponse> getAllOrders(String customerId) {
        Customer customer = customerServices.getCustomer(customerId);
        List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
        customer.getOrders().forEach(order -> {
            orderDetailResponses.add(generateOrderResponse(order));
        });
        return orderDetailResponses;


    }


    public List<OrderBasicDetail> getOrderBasicDetails(String userName) {
        Customer customer = customerServices.getCustomer(userName);
        List<OrderBasicDetail> orderBasicDetails = new ArrayList<>();
        customer.getOrders().forEach(order -> {
            OrderBasicDetail orderBasicDetail = OrderBasicDetail.builder()
                    .orderId(order.getIdentifier())
                    .orderDate(order.getOrderDate().toString())
                    .orderStatus(order.getStatus())
                    .grandTotal(order.getGrandTotal())
                    .generatedOrderId(order.getGeneratedOrderId())
                    .build();
            orderBasicDetails.add(orderBasicDetail);
        });
        return orderBasicDetails;
    }

    public OrderDetailResponse getOrderDetails(String orderId) {
        Orders order = ordersRepo.findByIdentifier(orderId).orElseThrow(() -> new RuntimeException("order not found"));
        return generateOrderResponse(order);
    }

    public List<TrackingData> getOrderItemTrackingData(String orderItemId) {
    return null;
    }

    public OrderInfo getCustomerOrdersInfo(String userName){
       var orders = ordersRepo.findAlByCustomerUserEmail(userName);

        long totalOrders = 0;
        long awaitingShipmentCount = 0;
        long awaitingDeliveryCount = 0;
        long pendingPaymentCount = 0;

        for (Orders order : orders.orElseThrow(()-> new RuntimeException("no orders Yet"))) {
            totalOrders++;

            switch (order.getStatus()) {
                case ORDERED -> awaitingShipmentCount++;
                case SHIPPED -> awaitingDeliveryCount++;

                default -> {
                }
            }

            if (!order.isPaymentStatus()) {
                pendingPaymentCount++;
            }
        }

        return  OrderInfo.builder()
                .allOrders(totalOrders)
                .awaitingDelivery(awaitingDeliveryCount)
                .awaitingShipment(awaitingShipmentCount)
                .awaitingPayment(pendingPaymentCount)
                .build();
    }

    public String verifyPurchase(String purchaseId){
        var history = purchaseHistory.findByIdentifier(purchaseId);
        if(history == null){
            return "invalid";
        }else {
            return "valid";
        }
    }


    public String validateReviewExists(String purchaseId,String userName){
        var history = purchaseHistory.findByIdentifier(purchaseId);
        var reviews = reviewsRepo.findByUserPurchaseHistoryId(history.getId());
        if(reviews == null){
            return "valid";
        }else {
            return "invalid";
        }
    }


    public ReviewDto getReviewMetaInfo(String userName, String purchaseId){
        var history = purchaseHistory.findByIdentifier(purchaseId);
            return ReviewDto.builder()
                    .productImage(ImplFunction.parseImageURL(history.getProduct().getImages()).get(0))
                    .productName(history.getProduct().getName())
                    .productId(history.getProduct().getIdentifier())
                    .purchaseDate(history.getPurchaseDate())
                    .build();
    }

    public ReviewDto ReviewData(String userName, String purchaseId){
        var history = purchaseHistory.findByIdentifier(purchaseId);
        var reviews = reviewsRepo.findByUserPurchaseHistoryId(history.getId());
        var reviewImages = reviewResourceRepo.findAllByReviewId(reviews.getId());

            return ReviewDto.builder()
                    .productImage(ImplFunction.parseImageURL(history.getProduct().getImages()).get(0))
                    .productName(history.getProduct().getName())
                    .productId(history.getProduct().getIdentifier())
                    .reviewText(reviews.getReview())
                    .rating(reviews.getRating())
                    .headline(reviews.getHeadline())
                    .purchaseId(reviews.getUserPurchaseHistory().getIdentifier())
                    .images(ImplFunction.parseImageURLs(reviewImages))
                    .purchaseDate(history.getPurchaseDate())
                    .build();

    }


}
