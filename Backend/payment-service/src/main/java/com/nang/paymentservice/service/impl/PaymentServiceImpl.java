package com.nang.paymentservice.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nang.paymentservice.dto.request.GetPaymentByStatusAndDateReq;
import com.nang.paymentservice.dto.request.PaymentRequest;
import com.nang.paymentservice.dto.response.PaymentResponse;
import com.nang.paymentservice.entity.Payment;
import com.nang.paymentservice.repository.PaymentRepository;
import com.nang.paymentservice.service.PaymentService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {
    PaymentRepository paymentRepository;

    @Override
    public PaymentResponse savePayment(PaymentRequest request) {
        Payment payment = new Payment(request);
        paymentRepository.save(payment);
        return new PaymentResponse(payment);
    }

    @Override
    public List<PaymentResponse> getPaymentByOrderId(String orderId) {
        List<Payment> payments = paymentRepository.findPaymentsByOrderId(orderId);
        return payments.stream().map(PaymentResponse::new).toList();
    }

    @Override
    public List<PaymentResponse> getPaymentByStatusAndTime(GetPaymentByStatusAndDateReq request) {
        LocalDateTime statTime = request.getStartDate().atStartOfDay();
        LocalDateTime endDateTime = request.getEndDate().plusDays(1).atStartOfDay();
        List<Payment> payments =
                paymentRepository.findPaymentsByStatusAndDate(request.getStatus(), statTime, endDateTime);
        return payments.stream().map(PaymentResponse::new).toList();
    }


    @Override
    public List<PaymentResponse> getPaymentsByOrderIdsAndStatus(List<String> orderIds, Integer status) {
        List<Payment> payments = paymentRepository.findByOrderIdsAndStatus(orderIds, status);
        return payments.stream().map(PaymentResponse::new).toList();
    }
}
