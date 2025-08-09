package com.example.apigateway.service;

import com.example.apigateway.dto.TpsMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class TpsProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${tps.topic-name}")
    private String tpsTopic;

    public TpsProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTpsMetric(int tps) {
        TpsMessage message = new TpsMessage(LocalDateTime.now(), tps);
        log.info("send tps message to topic: {} with message: {}" , tpsTopic, message);
        kafkaTemplate.send(tpsTopic, message);
    }
}
