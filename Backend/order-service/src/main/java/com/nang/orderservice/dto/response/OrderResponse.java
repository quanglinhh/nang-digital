package com.nang.orderservice.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nang.orderservice.entity.Order;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {
    String orderId;
    String deviceId;
    Integer status;
    String customerName;
    String customerPhone;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String description;
    BigDecimal price;

    public OrderResponse(Order order) {
        this.orderId = order.getId();
        this.deviceId = order.getDeviceId();
        this.status = order.getStatus();
        this.customerName = order.getCustomerName();
        this.customerPhone = order.getCustomerPhone();
        this.description = order.getDescription();
        this.price = order.getPrice();
        this.startTime = order.getStartTime();
        this.endTime = order.getEndTime();
    }


}
