package com.nang.orderservice.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.nang.orderservice.dto.response.OrderResponse;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentRequest {
    String orderId;
    BigDecimal price;
    Integer status;
    LocalDateTime rentTime;

    public PaymentRequest(OrderResponse order) {
        this.orderId = order.getOrderId();
        this.status = order.getStatus();
        this.price = order.getPrice();
        this.rentTime = order.getStartTime();
    }
}
