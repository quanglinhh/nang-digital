package com.nang.reportservice.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nang.reportservice.dto.TpsMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class TpsListenerService {
    private final RedisTemplate<String, String> redisTemplate;
    private static final String TPS_KEY = "tps_data";


    @KafkaListener(
            topics = "${tps.topic-name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listenTps(TpsMessage message) throws JsonProcessingException {
        log.info("Received TPS message: {}", message);

        ObjectMapper objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();

        String jsonMessage = objectMapper.writeValueAsString(message);
        // 2. Lưu dữ liệu gốc vào Sorted Set với score là epochSecond
        long score = message.getTimestamp().toEpochSecond(ZoneOffset.UTC);
        redisTemplate.opsForZSet().add(TPS_KEY, jsonMessage, score);

        // 2. Cập nhật tổng TPS theo minute, hour, day
        updateAggregate("minutes", formatKey("minutes", message.getTimestamp()), message.getTps(), Duration.ofMinutes(15));
        updateAggregate("hours", formatKey("hours", message.getTimestamp()), message.getTps(), Duration.ofHours(2));
        updateAggregate("days", formatKey("days", message.getTimestamp()), message.getTps(), Duration.ofDays(2));

        // 3. Xóa dữ liệu cũ hơn 10 phút
        long cutoff = message.getTimestamp()
                .minusMinutes(10)
                .toEpochSecond(ZoneOffset.UTC);
        redisTemplate.opsForZSet().removeRangeByScore(TPS_KEY, 0, cutoff - 1);
    }

    /** Cập nhật giá trị tổng TPS cho một key */
    private void updateAggregate(String type, String key, int tps, Duration ttl) {
        redisTemplate.opsForValue().increment(key, tps);
        redisTemplate.expire(key, ttl);

        log.info("[updateAggregate] Type: {}, Key: {}, Increment: {}, TTL: {}s",
                type, key, tps, ttl.toSeconds());
    }

    /** Tạo key theo loại: minutes, hours, days */
    private String formatKey(String type, LocalDateTime timestamp) {
        switch (type) {
            case "minutes":
                return String.format("TPS:minutes_%s", timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm")));
            case "hours":
                return String.format("TPS:hours_%s", timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH")));
            case "days":
                return String.format("TPS:days_%s", timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            default:
                throw new IllegalArgumentException("Invalid type: " + type);
        }
    }

}
