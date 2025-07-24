package com.nang.orderservice.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateOrderRequest {
    String orderId;
    String deviceId;
    Integer status;
    String customerName;
    String customerPhone;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String description;
    BigDecimal price;
}
