package com.example.energy_community_producer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class EnergyProductionScheduler {
    private final MessagingService messagingService;

    private final ShortwaveRadiationService shortwaveRadiationService;

    public EnergyProductionScheduler(MessagingService messagingService, ShortwaveRadiationService shortwaveRadiationService) {
        this.messagingService = messagingService;
        this.shortwaveRadiationService = shortwaveRadiationService;
    }

    @Scheduled(fixedDelay = 3000)
    public void produce() {

        double shortwaveRadiationFactor = this.shortwaveRadiationService.getShortwaveRadiationFactor();

        double random = Math.random() * 2;

        double producedEnergy = shortwaveRadiationFactor * random;

        String message = "{\"type\": \"producer\", \"association\": \"community\", \"kwh\": " +  producedEnergy + ", \"datetime\": \"" + LocalDateTime.now() + "\"}";

        messagingService.sendMessage(message);
    }
}
