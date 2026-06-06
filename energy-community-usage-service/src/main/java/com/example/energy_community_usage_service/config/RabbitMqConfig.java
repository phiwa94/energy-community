package com.example.energy_community_usage_service.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RabbitMqConfig {
    public static final String PRODUCER_QUEUE = "community_energy_producer_out";
    public static final String USER_QUEUE = "community_energy_user_out";
    public static final String USAGE_UPDATED_QUEUE = "usage_data_updated";

    @Bean
    public Queue producerQueue() {
        return new Queue(PRODUCER_QUEUE, true);
    }

    @Bean
    public Queue userQueue() {
        return new Queue(USER_QUEUE, true);
    }

    @Bean
    public Queue usageUpdatedQueue() {
        return new Queue(USAGE_UPDATED_QUEUE, true);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
