package com.example.apigateway.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Value("${tps.topic-name}")
    private String tpsTopicName;

    @Bean
    public NewTopic createTpsTopic() {
        return TopicBuilder.name(tpsTopicName)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
