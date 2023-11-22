package com.Onestop.ecommerce.Service.Customer;


import com.Onestop.ecommerce.Dto.CustomerDto.Orders.OrderDetailResponse;
import com.Onestop.ecommerce.Dto.CustomerDto.Orders.OrderRequest;
import com.Onestop.ecommerce.Dto.CustomerDto.Orders.OrderResponse;

import java.util.List;

public interface OrdersService {
  String createOrder(OrderRequest orderRequest);
  OrderResponse getOrder(String orderId);
  String cancelOrder(String orderId);
  List<OrderDetailResponse> getAllOrders(String customerId);
}
