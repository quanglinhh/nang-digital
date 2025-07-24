package com.nang.paymentservice.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.nang.paymentservice.dto.request.PaymentRequest;

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
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String orderId;
    BigDecimal price;
    Integer status;
    LocalDateTime rentTime;

    @CreatedDate
    protected LocalDateTime createdAt;

    @LastModifiedDate
    protected LocalDateTime updatedAt;

    private Integer isDel = 0;

    private Integer isActive = 1;

    public Payment(PaymentRequest paymentRequest) {
        this.orderId = paymentRequest.getOrderId();
        this.price = paymentRequest.getPrice();
        this.status = paymentRequest.getStatus();
        this.rentTime = paymentRequest.getRentTime();
    }
}
