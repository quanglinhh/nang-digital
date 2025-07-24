package com.nang.orderservice.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheckDuplicateOrderReq {
    LocalDate rentDate; // Ngày thuê
    LocalDateTime startTime; // Giờ bắt đầu
    LocalDateTime endTime; // Giờ kết thúc
    String deviceId;
}
