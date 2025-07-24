package com.nang.orderservice.dto.request;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetPaymentByStatusAndDateReq {
    String status;
    LocalDate startDate;
    LocalDate endDate;
}
