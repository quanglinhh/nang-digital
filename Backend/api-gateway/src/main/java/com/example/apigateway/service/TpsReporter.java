package com.example.apigateway.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TpsReporter {
    final TpsProducerService tpsProducerService;
    private final AtomicInteger counter = new AtomicInteger(0);

    public void increment() {
        counter.incrementAndGet();
    }

    @Scheduled(fixedRate = 1000)
    public void reportTps() {
        int tps = counter.getAndSet(0);

        //test
        tps = new java.util.Random().nextInt(100);

        tpsProducerService.sendTpsMetric(tps);
    }
}
