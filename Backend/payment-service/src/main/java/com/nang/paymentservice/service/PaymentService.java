package com.nang.paymentservice.service;

import java.util.List;

import com.nang.paymentservice.dto.request.GetPaymentByStatusAndDateReq;
import com.nang.paymentservice.dto.request.PaymentRequest;
import com.nang.paymentservice.dto.response.PaymentResponse;

public interface PaymentService {
    PaymentResponse savePayment(PaymentRequest request);

    List<PaymentResponse> getPaymentByOrderId(String orderId);

    List<PaymentResponse> getPaymentByStatusAndTime(GetPaymentByStatusAndDateReq request);

    List<PaymentResponse> getPaymentsByOrderIdsAndStatus(List<String> orderIds, Integer status);
}
