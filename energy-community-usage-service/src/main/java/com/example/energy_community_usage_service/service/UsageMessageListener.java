package com.example.energy_community_usage_service.service;

import com.example.energy_community_usage_service.config.RabbitMqConfig;
import com.example.energy_community_usage_service.dto.EnergyMessage;
import com.example.energy_community_usage_service.model.HourlyUsage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class UsageMessageListener {

    private final ObjectMapper objectMapper;
    private final UsageCalculationService usageCalculationService;
    private final RabbitTemplate rabbitTemplate;

    public UsageMessageListener(ObjectMapper objectMapper,
                                UsageCalculationService usageCalculationService,
                                RabbitTemplate rabbitTemplate) {
        this.objectMapper = objectMapper;
        this.usageCalculationService = usageCalculationService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = RabbitMqConfig.PRODUCER_QUEUE)
    public void receiveProducerMessage(String jsonMessage) {
        process(jsonMessage);
    }

    @RabbitListener(queues = RabbitMqConfig.USER_QUEUE)
    public void receiveUserMessage(String jsonMessage) {
        process(jsonMessage);
    }

    private void process(String jsonMessage) {
        try {
            EnergyMessage energyMessage =
                    objectMapper.readValue(jsonMessage, EnergyMessage.class);

            HourlyUsage updatedUsage =
                    usageCalculationService.processMessage(energyMessage);

            String updateMessage = "{\"hour\": \"" + updatedUsage.getHourStart() + "\"}";

            rabbitTemplate.convertAndSend(
                    RabbitMqConfig.USAGE_UPDATED_QUEUE,
                    updateMessage
            );

            System.out.println("Processed usage message: " + jsonMessage);
            System.out.println(
                    "Updated hour: " + updatedUsage.getHourStart()
                            + ", communityProduced=" + updatedUsage.getCommunityProduced()
                            + ", communityUsed=" + updatedUsage.getCommunityUsed()
                            + ", gridUsed=" + updatedUsage.getGridUsed()
            );

        } catch (Exception exception) {
            System.out.println("Error while processing usage message: " + jsonMessage);
            System.out.println(exception.getMessage());
        }
    }
}