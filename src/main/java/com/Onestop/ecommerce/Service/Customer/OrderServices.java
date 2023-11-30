package com.Onestop.ecommerce.Service.Customer;

import com.Onestop.ecommerce.Dto.CustomerDto.Orders.*;
import com.Onestop.ecommerce.Entity.Customer.Customer;
import com.Onestop.ecommerce.Entity.Customer.address.Address;
import com.Onestop.ecommerce.Entity.Customer.card.Cards;
import com.Onestop.ecommerce.Entity.Customer.cart.Cart;
import com.Onestop.ecommerce.Entity.orders.CancelOrders;
import com.Onestop.ecommerce.Entity.orders.OrderItems;
import com.Onestop.ecommerce.Entity.orders.OrderStatus;
import com.Onestop.ecommerce.Entity.orders.Orders;
import com.Onestop.ecommerce.Entity.products.Product;
import com.Onestop.ecommerce.Repository.CustomerRepo.*;
import com.Onestop.ecommerce.Repository.CustomerRepo.OrdersRepo.CancelOrdersRepo;
import com.Onestop.ecommerce.Repository.CustomerRepo.OrdersRepo.OrderItemsRepo;
import com.Onestop.ecommerce.Repository.CustomerRepo.OrdersRepo.OrdersRepo;
import com.Onestop.ecommerce.Repository.LogisticsRepo.WareHouseRepo;
import com.Onestop.ecommerce.Repository.products.productsRepo;
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


    @Override
    @Transactional
    public String createOrder(OrderRequest orderRequest) {
//        boolean paymentStatus = completePayment(orderRequest);
        Customer customer = customerServices.getCustomer(orderRequest.getCustomerId());
        Address shippingAddress = addressRepo.findByIdentifier(orderRequest.getShippingAddressId()).orElseThrow(() -> new RuntimeException("address not found"));
        Address billingAddress = addressRepo.findByIdentifier(orderRequest.getBillingAddressId()).orElseThrow(() -> new RuntimeException("address not found"));
        Cart cart = customer.getCart();
        Cards card = cardsRepo.findByIdentifier(orderRequest.getCardId()).orElseThrow(() -> new RuntimeException("card not found"));
        List<OrderItems> orderItems = new ArrayList<>();

        cart.getItems().forEach(item -> {
            Product product = productRepo.findByIdentifier(item.getProduct().getIdentifier()).orElseThrow(() -> new RuntimeException("product not found"));
//            while (product.isEnabled()) {
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
        List<List<OrderItems>> orderItemsList = batchOrders(orderItems);


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
//                    .paymentStatus(paymentStatus)
                    .status(OrderStatus.ORDERED)
                    .paymentMethod(orderRequest.getPaymentMethod())
                    .wareHouse(wareHouse)
                    .build();
            ordersRepo.save(order);
            customer.getOrders().add(order);
            wareHouse.getOrders().add(order);
            wareHouseRepo.save(wareHouse);
            customerRepo.save(customer);
        });
        updateInventory(orderItems);
        cartServices.emptyCart(customer.getUser().getEmail());

        return "ORDER_PLACED";
    }

    private double orderTotal(List<OrderItems> orderItems) {
        return orderItems.stream().mapToDouble(OrderItems::getItemTotal).sum();
    }

    private boolean completePayment(OrderRequest orderRequest) {
        try {
            Charge charge = Charge.create(
                    new ChargeCreateParams.Builder()
                            .setAmount((long) (orderRequest.getOrdertotal() * 100))
                            .setCurrency("inr")
                            .setSource(orderRequest.getPaymentProcessId())
                            .build()
            );
            return charge.getPaid();
        } catch (Exception e) {
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
