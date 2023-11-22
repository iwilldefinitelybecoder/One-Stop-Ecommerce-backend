package com.Onestop.ecommerce.Service.Customer;

import com.Onestop.ecommerce.Dto.CustomerDto.Orders.*;
import com.Onestop.ecommerce.Entity.Customer.Customer;
import com.Onestop.ecommerce.Entity.Customer.address.Address;
import com.Onestop.ecommerce.Entity.Customer.card.Cards;
import com.Onestop.ecommerce.Entity.Customer.cart.Cart;
import com.Onestop.ecommerce.Entity.orders.OrderItems;
import com.Onestop.ecommerce.Entity.orders.OrderStatus;
import com.Onestop.ecommerce.Entity.orders.Orders;
import com.Onestop.ecommerce.Entity.products.Product;
import com.Onestop.ecommerce.Repository.CustomerRepo.AddressRepo;
import com.Onestop.ecommerce.Repository.CustomerRepo.CardsRepo;
import com.Onestop.ecommerce.Repository.CustomerRepo.CustomerRepo;
import com.Onestop.ecommerce.Repository.CustomerRepo.OrdersRepo;
import com.Onestop.ecommerce.Repository.LogisticsRepo.WareHouseRepo;
import com.Onestop.ecommerce.Repository.products.productsRepo;
import com.Onestop.ecommerce.Service.products.ProductsServices;
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
public class OrderServices implements OrdersService{


    private final AddressRepo addressRepo;
    private final CardsRepo cardsRepo;

    private final OrdersRepo ordersRepo;
    private final productsRepo productRepo;
    private final WareHouseRepo wareHouseRepo;
    private final CustomerRepo customerRepo;
    private final CustomerServices customerServices;




    @Override
    @Transactional
    public String createOrder(OrderRequest orderRequest) {
//        boolean paymentStatus = completePayment(orderRequest);
        Customer customer = customerServices.getCustomer(orderRequest.getCustomerId());
        Address shippingAddress = addressRepo.findByIdentifier(orderRequest.getShippingAddressId()).orElseThrow(()-> new RuntimeException("address not found")) ;
        Address billingAddress = addressRepo.findByIdentifier(orderRequest.getBillingAddressId()).orElseThrow(()-> new RuntimeException("address not found")) ;
        Cart cart = customer.getCart();
        Optional<Cards> card = cardsRepo.findByIdentifier(orderRequest.getCardId()) ;
        List<OrderItems> orderItems = new ArrayList<>();

        cart.getItems().forEach(item ->  {
            Product product = productRepo.findByIdentifier(item.getProduct().getIdentifier()).orElseThrow(()-> new RuntimeException("product not found"));
//            while (product.isEnabled()) {
                var orderItem = OrderItems.builder()
                        .product(product)
                        .quantity(item.getQuantity())
                        .itemTotal(item.getQuantity() * product.getSalePrice())
                        .itemPrice(product.getSalePrice())
                        .vendor(product.getVendor())
                        .build();
                orderItems.add(orderItem);
                   });
        List<List<OrderItems>> orderItemsList = batchOrders(orderItems);



        orderItemsList.forEach(orderItems1 -> {
            var wareHouse = wareHouseRepo.findByIdentifier(orderItems1.get(0).getProduct().getWareHouse().getIdentifier()).orElseThrow(()-> new RuntimeException("warehouse not found"));
            log.info(String.valueOf(wareHouse));
            var order = Orders.builder()
                    .orderDate(new Date())
                    .orderItems(orderItems1)
                    .billingAddress(billingAddress)
                    .shippingAddress(shippingAddress)
                    .paymentCard(card.orElse(null))
                    .customer(customer)
                    .total(orderTotal(orderItems1))
//                    .paymentStatus(paymentStatus)
                    .status(OrderStatus.ORDERED)
                    .paymentMethod(orderRequest.getPaymentMethod())
                    .build();
            order.setGeneratedOrderId(generateOrderId(order.getId()));
            customer.getOrders().add(order);
            ordersRepo.save(order);
            wareHouse.getOrders().add(order);
            wareHouseRepo.save(wareHouse);
            customerRepo.save(customer);
        });
        updateInventory(orderItems);

        return "ORDER_PLACED";
    }

    private double orderTotal(List<OrderItems> orderItems){
        return orderItems.stream().mapToDouble(OrderItems::getItemTotal).sum();
    }

    private boolean completePayment(OrderRequest orderRequest){
        try {
            Charge charge = Charge.create(
                    new ChargeCreateParams.Builder()
                            .setAmount((long) (orderRequest.getOrdertotal() * 100))
                            .setCurrency("inr")
                            .setSource(orderRequest.getPaymentProcessId())
                            .build()
            );
            return charge.getPaid();
        }catch (Exception e){
            log.error("error while processing payment",e);
            return false;
        }

    }

    private List<List<OrderItems>> batchOrders(List<OrderItems> orderItems){
        log.info(String.valueOf(orderItems));
        Map<String,List<OrderItems>> segregatedOrders = new HashMap<>();
        for(OrderItems orderItem : orderItems){
            String wareHouseId = orderItem.getProduct().getWareHouse().getIdentifier();
            segregatedOrders.computeIfAbsent(wareHouseId,k->new ArrayList<>()).add(orderItem);
        }
        log.info(segregatedOrders.toString());
        return new ArrayList<>(segregatedOrders.values());
    }

    private void updateInventory(List<OrderItems> orderItems){
        orderItems.forEach(orderItem -> {
            var product = orderItem.getProduct();
            var inventory = product.getWareHouse().getInventory();
            inventory.forEach(productInventory -> {
                if(productInventory.getProduct().equals(product)){
                    productInventory.setStock(productInventory.getStock()-orderItem.getQuantity());
                }
            });

        });
    }

    private String generateOrderId(Long orderId) {
        String prefix = "ONESTP"; // Prefix kept inside the method
        String orderIds = prefix + orderId;
        return orderIds;
    }


    @Override
    public OrderResponse getOrder(String orderId) {
        return null;
    }

    @Override
    public String cancelOrder(String orderId) {
        return null;
    }

    @Override
    public List<OrderDetailResponse> getAllOrders(String customerId) {
        Customer customer = customerServices.getCustomer(customerId);
        List<OrderDetailResponse> orderResponses = new ArrayList<>();
        customer.getOrders().forEach(order -> {
            List<ProductListOfOrder> productListOfOrders = new ArrayList<>();
            order.getOrderItems().forEach(orderItems -> {
                ProductListOfOrder productListOfOrder = ProductListOfOrder.builder()
                        .productId(orderItems.getProduct().getIdentifier())
                        .quantity(orderItems.getQuantity())
                        .price(orderItems.getItemPrice())
                        .totalPrice(orderItems.getItemTotal())
                        .build();
                productListOfOrders.add(productListOfOrder);
            });
            var summary = OrderSummary.builder()
                    .itemsTotal(order.getTotal())
                    .shippingTotal(order.getShippingTotal())
                    .taxTotal(order.getTaxTotal())
                    .grandTotal(order.getGrandTotal())
                    .build();

            OrderDetailResponse detailResponse = OrderDetailResponse.builder()
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
                    .orderId(order.getGeneratedOrderId())
                    .TrackingId(order.getTrackingId())
                    .orderSummary(summary)
                            .build();

            orderResponses.add(detailResponse);
        });
        return orderResponses;
    }
}
