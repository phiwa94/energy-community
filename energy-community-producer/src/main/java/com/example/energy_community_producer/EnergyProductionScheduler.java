package com.example.energy_community_producer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class EnergyProductionScheduler {
    private final MessagingService messagingService;

    private final ShortwaveRadiationService shortwaveRadiationService = new ShortwaveRadiationService();

    public EnergyProductionScheduler(MessagingService messagingService) {
        this.messagingService = messagingService;
    }

    @Scheduled(fixedDelay = 3000)
    public void produce() {

        int shortwaveRadiationFactor = this.shortwaveRadiationService.getShortwaveRadiationFactor();

        double random = Math.random() * 2;

        double producedEnergy = /*shortwaveRadiationFactor*/ 100 * random;

        String message = "{\"type\": \"producer\", \"association\": \"community\", \"kwh\": " +  producedEnergy + ", \"datetime\": \"" + LocalDateTime.now() + "\"}";

        messagingService.sendMessage(message);
    }
}
