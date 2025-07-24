package com.nang.orderservice.repository.httpClient;

import com.nang.orderservice.dto.request.GetPaymentByOrderIdAndStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.nang.orderservice.configuration.AuthenticationRequestInterceptor;
import com.nang.orderservice.dto.request.GetPaymentByStatusAndDateReq;
import com.nang.orderservice.dto.request.PaymentRequest;
import com.nang.orderservice.dto.response.ApiResponse;
import com.nang.orderservice.dto.response.PaymentResponse;

import java.util.List;

@FeignClient(
        name = "payments",
        url = "http://localhost:8083/payments",
        configuration = {AuthenticationRequestInterceptor.class})
public interface PaymentClient {
    @PostMapping(value = "/save",consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<PaymentResponse> savePayment(@RequestBody PaymentRequest request);

    @GetMapping(value = "/getByOrderId/{orderId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<List<PaymentResponse>> getByOrderId(@PathVariable("orderId") String orderId);

    @PostMapping(value = "/getByStatusAndDate", consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<List<PaymentResponse>> getByStatusAndDate(@RequestBody GetPaymentByStatusAndDateReq req);

    @PostMapping(value = "/getByStatusAndOrderIds", consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<List<PaymentResponse>> getByStatusAndOrderIds(@RequestBody GetPaymentByOrderIdAndStatus req);

}
