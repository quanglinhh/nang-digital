package com.nang.reportservice.service;

import com.nang.reportservice.dto.response.TpsResponse;
import com.nang.reportservice.enumm.TpsPeriod;

import java.util.List;

public interface TpsService {
    List<TpsResponse> getTpsByPeriod(TpsPeriod period);
}
