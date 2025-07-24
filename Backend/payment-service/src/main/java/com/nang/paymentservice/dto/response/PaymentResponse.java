package com.nang.paymentservice.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.nang.paymentservice.entity.Payment;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse {
    String orderId;
    BigDecimal price;
    Integer status;
    LocalDateTime rentTime;
    LocalDateTime createdTime;

    public PaymentResponse(Payment payment) {
        this.orderId = payment.getOrderId();
        this.price = payment.getPrice();
        this.status = payment.getStatus();
        this.rentTime = payment.getRentTime();
        this.createdTime = payment.getCreatedAt();
    }
}
