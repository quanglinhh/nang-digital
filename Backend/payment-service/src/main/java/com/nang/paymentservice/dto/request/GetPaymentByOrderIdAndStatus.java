package com.nang.paymentservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetPaymentByOrderIdAndStatus {
    List<String> orderIds;
    Integer status;
}
