package com.example.energy_community_percentage_service.service;

import com.example.energy_community_percentage_service.config.RabbitMqConfig;
import com.example.energy_community_percentage_service.dto.UsageUpdatedMessage;
import com.example.energy_community_percentage_service.model.CurrentPercentage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UsageUpdatedListener {

    private final ObjectMapper objectMapper;
    private final PercentageCalculationService percentageCalculationService;

    public UsageUpdatedListener(ObjectMapper objectMapper,
                                PercentageCalculationService percentageCalculationService) {
        this.objectMapper = objectMapper;
        this.percentageCalculationService = percentageCalculationService;
    }

    @RabbitListener(queues = RabbitMqConfig.USAGE_UPDATED_QUEUE)
    public void receiveUsageUpdatedMessage(String jsonMessage) {
        try {
            UsageUpdatedMessage usageUpdatedMessage =
                    objectMapper.readValue(jsonMessage, UsageUpdatedMessage.class);

            LocalDateTime hourStart = LocalDateTime.parse(usageUpdatedMessage.getHour());

            CurrentPercentage currentPercentage =
                    percentageCalculationService.calculateAndSave(hourStart);

            System.out.println("Processed usage update message: " + jsonMessage);
            System.out.println(
                    "Updated percentage for hour: " + currentPercentage.getHourStart()
                            + ", communityDepleted=" + currentPercentage.getCommunityDepleted()
                            + ", gridPortion=" + currentPercentage.getGridPortion()
            );

        } catch (Exception exception) {
            System.out.println("Error while processing usage update message: " + jsonMessage);
            System.out.println(exception.getMessage());
        }
    }
}
