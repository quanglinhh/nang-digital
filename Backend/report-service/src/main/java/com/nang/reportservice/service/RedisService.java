package com.nang.reportservice.service;

import com.nang.reportservice.dto.TpsMessage;

import java.util.List;

public interface RedisService {
    List<TpsMessage> getRealtimeTpsData();

    long getTpsDataByKey(String key);
}
