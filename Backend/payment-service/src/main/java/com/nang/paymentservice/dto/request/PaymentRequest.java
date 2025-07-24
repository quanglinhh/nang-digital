package com.nang.paymentservice.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
}
