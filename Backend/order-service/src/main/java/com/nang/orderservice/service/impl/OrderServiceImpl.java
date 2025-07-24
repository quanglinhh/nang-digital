package com.nang.orderservice.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.nang.orderservice.dto.request.*;
import com.nang.orderservice.exception.AppException;
import com.nang.orderservice.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nang.orderservice.dto.response.OrderListResponse;
import com.nang.orderservice.dto.response.OrderResponse;
import com.nang.orderservice.dto.response.PaymentResponse;
import com.nang.orderservice.entity.Order;
import com.nang.orderservice.repository.OrderRepository;
import com.nang.orderservice.repository.httpClient.PaymentClient;
import com.nang.orderservice.service.OrderService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;

    PaymentClient paymentClient;

    @Override
    @Transactional
    public OrderResponse saveOrder(CreateOrderRequest request) {
        Order order = new Order(request);

        if(StringUtils.isEmpty(
                request.getOrderId()) &&
                orderRepository.isDuplicateTime(order.getDeviceId(),
                        order.getStartTime(),
                        order.getEndTime())) {
                throw new AppException(ErrorCode.DUPLICATE_REND_TIME);
        }
        orderRepository.save(order);
        OrderResponse orderResponse = new OrderResponse(order);
        paymentClient.savePayment(new PaymentRequest(orderResponse));
        return orderResponse;
    }

    @Override
    public boolean checkDuplicateTime(CheckDuplicateOrderReq req) {
        return orderRepository.isDuplicateTime(req.getDeviceId(),  req.getStartTime(), req.getEndTime());
    }

    @Override
    public List<OrderResponse> getAllOrderByDeviceId(String deviceId) {
        List<Order> orders = orderRepository.findByDeviceId(deviceId);
        return orders.stream().map(OrderResponse :: new).toList();
    }

    @Override
    public List<OrderResponse> getAllOrderByDeviceIdAndDate(OrderByDeviceAndDateReq request) {
        LocalDateTime startTime = request.getStartDate().atStartOfDay();
        LocalDateTime endTime = request.getEndDate().plusDays(1).atStartOfDay();
        List<Order> orders = orderRepository.findByDeviceIdAndDate(request.getDeviceId(), startTime, endTime);
        return orders.stream().map(OrderResponse :: new).toList();
    }

    @Override
    public List<OrderResponse> getAllOrderByOrderId(String orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }
        List<PaymentResponse> paymentResponses = paymentClient.getByOrderId(orderId).getResult();
        log.info("get all payment responses: {}", paymentResponses);
        return paymentResponses.stream().map(p -> {
            OrderResponse orderResponse = new OrderResponse(order.get());
            orderResponse.setPrice(p.getPrice());
            orderResponse.setStatus(p.getStatus());
            return orderResponse;
        }).toList();
    }

    @Override
    public OrderListResponse getOrdersByDeviceIdAndStatus(OrderByDeviceAndStatus request) {
        String deviceId = request.getDeviceId();
        Integer status = request.getStatus();
        LocalDateTime startTime = request.getStartDate().atStartOfDay();
        LocalDateTime endTime = request.getEndDate().plusDays(1).atStartOfDay();
        List<Order> orders = orderRepository.findByDeviceIdAndDate(deviceId, startTime, endTime);
        Map<String, Order> orderMap = orders.stream()
                .collect(Collectors.toMap(Order::getId, Function.identity()));

        List<String> orderIds = new ArrayList<>(orderMap.keySet());

        GetPaymentByOrderIdAndStatus getPaymentByOrderIdAndStatus =
                new GetPaymentByOrderIdAndStatus(orderIds, status);

        List<PaymentResponse> paymentResponses =
                paymentClient.getByStatusAndOrderIds(getPaymentByOrderIdAndStatus).getResult();

        BigDecimal totalPrice = BigDecimal.ZERO;

        List<OrderResponse> orderResponses = new ArrayList<>();

        for (PaymentResponse p : paymentResponses) {
            Order order = orderMap.get(p.getOrderId());
            if (order != null) {
                OrderResponse response = new OrderResponse(order);
                response.setPrice(p.getPrice());
                response.setStatus(p.getStatus());
                orderResponses.add(response);

                if (p.getPrice() != null) {
                    totalPrice = totalPrice.add(p.getPrice());
                }
            }
        }

        return new OrderListResponse(orderResponses, totalPrice);
    }

}
