package com.nang.orderservice.controller;

import com.nang.orderservice.dto.request.CheckDuplicateOrderReq;
import com.nang.orderservice.dto.request.CreateOrderRequest;
import com.nang.orderservice.dto.request.OrderByDeviceAndDateReq;
import com.nang.orderservice.dto.request.OrderByDeviceAndStatus;
import com.nang.orderservice.dto.response.ApiResponse;
import com.nang.orderservice.dto.response.OrderListResponse;
import com.nang.orderservice.dto.response.OrderResponse;
import org.springframework.web.bind.annotation.*;

import com.nang.orderservice.service.OrderService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderController {
    OrderService orderService;

    @PostMapping("/save")
    public ApiResponse<OrderResponse> saveOrder(@RequestBody CreateOrderRequest paymentRequest) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.saveOrder(paymentRequest))
                .build();
    }

    @PostMapping("/checkDuplicateTime")
    public ApiResponse<Boolean> checkDuplicateOrderTime(@RequestBody CheckDuplicateOrderReq request){
        return ApiResponse.<Boolean>builder()
                .result(orderService.checkDuplicateTime(request))
                .build();
    }

    @PostMapping("/getByDeviceIdAndDate")
    public ApiResponse<List<OrderResponse>> getByDeviceIdAndDate(@RequestBody OrderByDeviceAndDateReq request) {
        return ApiResponse.< List<OrderResponse>>builder()
                .result(orderService.getAllOrderByDeviceIdAndDate(request))
                .build();
    }

    @GetMapping("/getOrderId/{orderId}")
    public ApiResponse<List<OrderResponse>> getByOrderId(@PathVariable String orderId) {
        return ApiResponse.< List<OrderResponse>>builder()
                .result(orderService.getAllOrderByOrderId(orderId))
                .build();
    }

    @PostMapping("/geByDeviceAndStatus")
    public ApiResponse<OrderListResponse> getByDeviceIdAndDate(@RequestBody OrderByDeviceAndStatus request) {
        return ApiResponse.< OrderListResponse>builder()
                .result(orderService.getOrdersByDeviceIdAndStatus(request))
                .build();
    }
}
