package com.example.energy_community_percentage_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String USAGE_UPDATED_QUEUE = "usage_data_updated";

    @Bean
    public Queue usageUpdatedQueue() {
        return new Queue(USAGE_UPDATED_QUEUE, true);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
