package com.nang.reportservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nang.reportservice.dto.TpsMessage;
import com.nang.reportservice.enumm.TpsKey;
import com.nang.reportservice.service.RedisService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Service
public class RedisServiceImpl implements RedisService {
    RedisTemplate<String, String> redisTemplate;
    ObjectMapper objectMapper;

    @Override
    public List<TpsMessage> getRealtimeTpsData() {
        // Get all elements in the ZSET
        Set<String> rawMessages = redisTemplate.opsForZSet().range(TpsKey.NEAREST_10_MINUTES.getRedisKeyPrefix(), 0, -1);

        if (rawMessages == null || rawMessages.isEmpty()) {
            return List.of();
        }

        // Convert JSON strings back into TpsMessage objects
        return rawMessages.stream()
                .map(json -> {
                    try {
                        return objectMapper.readValue(json, TpsMessage.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Error deserializing TpsMessage from Redis", e);
                    }
                })
                .toList();
    }

    @Override
    public long getTpsDataByKey(String key) {
        String value = redisTemplate.opsForValue().get(key);
        return  (value != null) ? Long.parseLong(value) : 0;
    }
}
