package com.nang.orderservice.service;

import java.util.List;

import com.nang.orderservice.dto.request.CheckDuplicateOrderReq;
import com.nang.orderservice.dto.request.CreateOrderRequest;
import com.nang.orderservice.dto.request.OrderByDeviceAndDateReq;
import com.nang.orderservice.dto.request.OrderByDeviceAndStatus;
import com.nang.orderservice.dto.response.OrderListResponse;
import com.nang.orderservice.dto.response.OrderResponse;

public interface OrderService {
    OrderResponse saveOrder(CreateOrderRequest request);

    boolean checkDuplicateTime(CheckDuplicateOrderReq req);

    List<OrderResponse> getAllOrderByDeviceId(String deviceId);

    List<OrderResponse> getAllOrderByDeviceIdAndDate(OrderByDeviceAndDateReq request);

    List<OrderResponse> getAllOrderByOrderId(String orderId);

    OrderListResponse getOrdersByDeviceIdAndStatus(OrderByDeviceAndStatus request);
}
