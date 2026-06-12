package com.example.energy_community_user;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class EnergyUsageScheduler {
    private final MessagingService messagingService;

    public EnergyUsageScheduler(MessagingService messagingService) {
        this.messagingService = messagingService;
    }

    @Scheduled(fixedDelay = 3000)
    public void use() {

        double random = Math.random() * 2;

        double usedEnergy = random + (getPeak() ? 2 : 0);

        String message = "{\"type\": \"user\", \"association\": \"community\", \"kwh\": " +  usedEnergy + ", \"datetime\": \"" + LocalDateTime.now() + "\"}";

        messagingService.sendMessage(message);
    }


    private boolean getPeak() {
        LocalTime now = LocalTime.now();
        boolean isMorningPeak = !now.isBefore(LocalTime.of(7, 0)) && now.isBefore(LocalTime.of(9, 0));
        boolean isEveningPeak = !now.isBefore(LocalTime.of(19, 0)) && now.isBefore(LocalTime.of(22, 0));

        if(isMorningPeak || isEveningPeak) {
            return true;
        }
        return false;
    }
}
