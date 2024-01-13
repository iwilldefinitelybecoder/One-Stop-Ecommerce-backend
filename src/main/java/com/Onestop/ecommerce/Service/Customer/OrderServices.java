package com.Onestop.ecommerce.Service.Customer;

import com.Onestop.ecommerce.Dto.CustomerDto.Orders.*;
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
import com.Onestop.ecommerce.Repository.LogisticsRepo.OrderShipment;
import com.Onestop.ecommerce.Repository.LogisticsRepo.ShipmentUpdateRepo;
import com.Onestop.ecommerce.Repository.LogisticsRepo.WareHouseRepo;
import com.Onestop.ecommerce.Repository.TransactionRepo.PaymentLedeger;
import com.Onestop.ecommerce.Repository.VendorRepo.SalesHistoryRepo;
import com.Onestop.ecommerce.Repository.VendorRepo.SalesRepo;
import com.Onestop.ecommerce.Repository.products.productsRepo;
import com.Onestop.ecommerce.Service.products.CouponsServices;
import com.Onestop.ecommerce.configuration.PaymentKeys;
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

            orderRequest.getProducts().forEach(productOrderDetails -> {
                Product product = productRepo.findByIdentifier(productOrderDetails.getProductId()).orElseThrow(() -> new RuntimeException("product not found"));
                createHistory(product,customer,productOrderDetails.getQuantity());
                var orderItem = OrderItems.builder()
                        .product(product)
                        .quantity(productOrderDetails.getQuantity())
                        .itemTotal(productOrderDetails.getQuantity() * product.getSalePrice())
                        .itemPrice(product.getSalePrice())
                        .status(OrderStatus.ORDERED)
                        .vendor(product.getVendor())
                        .build();
                orderItems.add(orderItem);
            });
            if(orderRequest.getPaymentMethod().equals(PaymentMethods.DEBITCARD)) {

                log.info("card: {}", orderRequest.isBuyNow());
                paymentStatus = completePayment(orderRequest, customer, orderRequest.getOrdertotal());
            }
        }else {
            var customer1 = customerServices.getCustomer(orderRequest.getCustomerId());
            Cart cart = customer1.getCart();
            cart.getItems().forEach(item -> {
                Product product = productRepo.findByIdentifier(item.getProduct().getIdentifier()).orElseThrow(() -> new RuntimeException("product not found"));

                createHistory(product, customer1, item.getQuantity());


                var orderItem = OrderItems.builder()
                        .product(product)
                        .quantity(item.getQuantity())
                        .itemTotal(item.getQuantity() * product.getSalePrice())
                        .itemPrice(product.getSalePrice())
                        .status(OrderStatus.ORDERED)
                        .vendor(product.getVendor())
                        .build();
                orderItems.add(orderItem);
            });
            if(orderRequest.getPaymentMethod().equals(PaymentMethods.DEBITCARD)) {

                paymentStatus = completePayment(orderRequest, customer, cart.getGrandTotal());
            }
        }
        List<List<OrderItems>> orderItemsList = batchOrders(orderItems);


        boolean finalPaymentStatus = paymentStatus;
        orderItemsList.forEach(orderItems1 -> {
            var wareHouse = wareHouseRepo.findByIdentifier(orderItems1.get(0).getProduct().getWareHouse().getIdentifier()).orElseThrow(() -> new RuntimeException("warehouse not found"));
            var order = Orders.builder()
                    .orderDate(new Date())
                    .orderItems(orderItems1)
                    .billingAddress(billingAddress)
                    .shippingAddress(shippingAddress)
                    .paymentCard(card)
                    .customer(customer)
                    .total(orderTotal(orderItems1))
                    .paymentStatus(finalPaymentStatus)
                    .status(OrderStatus.ORDERED)
                    .paymentMethod(orderRequest.getPaymentMethod())
                    .wareHouse(wareHouse)
                    .build();
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
                    .trackingNumber(UUID.randomUUID().toString())
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

    private String createHistory(Product product, Customer customer, int quantity){
        SalesData salesData = salesRepo.findByProductIdentifier(product.getIdentifier());
        UserPurchaseHistory userPurchaseHistory = UserPurchaseHistory.builder()
                .customer(customer)
                .product(product)
                .quantity(quantity)
                .total(quantity * product.getSalePrice())
                .price(product.getSalePrice())
                .build();
        purchaseHistory.save(userPurchaseHistory);
       SalesHistory history = SalesHistory.builder()
               .date(new Date())
               .salesData(salesData)
               .Count(quantity)
               .saleTotal(quantity * product.getSalePrice())
               .build();
       salesData.getSalesHistory().add(history);
          salesHistoryRepo.save(history);
            salesRepo.save(salesData);

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
            ProductListOfOrder productListOfOrder = ProductListOfOrder.builder()
                    .productId(orderItems.getProduct().getIdentifier())
                    .quantity(orderItems.getQuantity())
                    .price(orderItems.getItemPrice())
                    .totalPrice(orderItems.getItemTotal())
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
//                .paymentId(order.getPaymentCard().getIdentifier())
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
}
