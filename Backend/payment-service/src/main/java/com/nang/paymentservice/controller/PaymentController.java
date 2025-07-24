package com.nang.paymentservice.controller;

import java.util.List;

import com.nang.paymentservice.dto.request.GetPaymentByOrderIdAndStatus;
import org.springframework.web.bind.annotation.*;

import com.nang.paymentservice.dto.request.GetPaymentByStatusAndDateReq;
import com.nang.paymentservice.dto.request.PaymentRequest;
import com.nang.paymentservice.dto.response.ApiResponse;
import com.nang.paymentservice.dto.response.PaymentResponse;
import com.nang.paymentservice.service.PaymentService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping()
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PaymentController {
    PaymentService paymentService;

    @PostMapping("/save")
    public ApiResponse<PaymentResponse> savePayment(@RequestBody PaymentRequest paymentRequest) {
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentService.savePayment(paymentRequest))
                .build();
    }

    @GetMapping("/getByOrderId/{orderId}")
    public ApiResponse<List<PaymentResponse>> getPaymentByOrderId(@PathVariable String orderId) {
        return ApiResponse.<List<PaymentResponse>>builder()
                .result(paymentService.getPaymentByOrderId(orderId))
                .build();
    }

    @PostMapping("/getByStatusAndDate")
    public ApiResponse<List<PaymentResponse>> getPaymentByStatusAndDate(
            @RequestBody GetPaymentByStatusAndDateReq request) {
        return ApiResponse.<List<PaymentResponse>>builder()
                .result(paymentService.getPaymentByStatusAndTime(request))
                .build();
    }

    @PostMapping("/getByStatusAndOrderIds")
    public ApiResponse<List<PaymentResponse>> getPaymentByStatusAndDate(
            @RequestBody GetPaymentByOrderIdAndStatus request) {
        return ApiResponse.<List<PaymentResponse>>builder()
                .result(paymentService.getPaymentsByOrderIdsAndStatus(request.getOrderIds(), request.getStatus()))
                .build();
    }
}
