package com.nang.orderservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderByDeviceAndStatus{
    String deviceId;
    LocalDate startDate;
    LocalDate endDate;
    Integer status;
}
