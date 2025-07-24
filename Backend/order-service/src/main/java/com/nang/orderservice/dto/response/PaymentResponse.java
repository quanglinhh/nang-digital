package com.nang.orderservice.dto.response;

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
public class PaymentResponse {
    String orderId;
    BigDecimal price;
    Integer status;
    LocalDateTime rentTime;
    LocalDateTime createdTime;
}
