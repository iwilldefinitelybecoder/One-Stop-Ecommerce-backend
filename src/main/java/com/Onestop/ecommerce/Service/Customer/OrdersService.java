package com.Onestop.ecommerce.Service.Customer;


import com.Onestop.ecommerce.Dto.CustomerDto.OrderInfo;
import com.Onestop.ecommerce.Dto.CustomerDto.Orders.OrderCancelRequest;
import com.Onestop.ecommerce.Dto.CustomerDto.Orders.OrderDetailResponse;
import com.Onestop.ecommerce.Dto.CustomerDto.Orders.OrderRequest;


import java.util.List;

public interface OrdersService {
  String createOrder(OrderRequest orderRequest);
  OrderDetailResponse getOrder(String orderId);
  String cancelOrder(OrderCancelRequest request);
  List<OrderDetailResponse> getAllOrders(String customerId);
  OrderInfo getCustomerOrdersInfo(String username);
}
