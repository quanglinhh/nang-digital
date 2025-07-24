package com.nang.orderservice.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.nang.orderservice.dto.request.CreateOrderRequest;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "Order_table")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String deviceId;
    Integer status;
    String customerName;
    String customerPhone;
    LocalDateTime startTime;
    LocalDateTime endTime;
    BigDecimal price;
    String description;

    @CreatedDate
    protected LocalDateTime createdAt;

    @LastModifiedDate
    protected LocalDateTime updatedAt;

    private Integer isDel = 0;

    private Integer isActive = 1;

    public Order(CreateOrderRequest request) {
        this.id = request.getOrderId();
        this.deviceId = request.getDeviceId();
        this.status = request.getStatus();
        this.customerName = request.getCustomerName();
        this.customerPhone = request.getCustomerPhone();
        this.description = request.getDescription();
        this.startTime = request.getStartTime();
        this.endTime = request.getEndTime();
        this.price = request.getPrice();
    }
}
