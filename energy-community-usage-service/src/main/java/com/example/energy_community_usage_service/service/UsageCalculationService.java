package com.example.energy_community_usage_service.service;

import com.example.energy_community_usage_service.dto.EnergyMessage;
import com.example.energy_community_usage_service.model.HourlyUsage;
import com.example.energy_community_usage_service.repository.HourlyUsageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class UsageCalculationService {
    private final HourlyUsageRepository hourlyUsageRepository;

    public UsageCalculationService(HourlyUsageRepository hourlyUsageRepository) {
        this.hourlyUsageRepository = hourlyUsageRepository;
    }

    @Transactional
    public HourlyUsage processMessage(EnergyMessage message) {
        LocalDateTime hourStart = LocalDateTime.parse(message.getDatetime()).truncatedTo(ChronoUnit.HOURS);
        HourlyUsage hourlyUsage = hourlyUsageRepository.findByHourStart(hourStart)
                .orElseGet(() -> new HourlyUsage(hourStart));

        String messageType = message.getType();
        String association = message.getAssociation();

        if (!"community".equals(association)) {
            throw new IllegalArgumentException("Unsupported association: " + message.getAssociation());
        }

        if ("producer".equals(messageType)) {
            addProducedEnergy(hourlyUsage, message.getKwh());
        } else if ("user".equals(messageType)) {
            addUsedEnergy(hourlyUsage, message.getKwh());
        } else {
            throw new IllegalArgumentException("Unsupported message type: " + message.getType());
        }

        return hourlyUsageRepository.save(hourlyUsage);
    }

    private void addProducedEnergy(HourlyUsage hourlyUsage, double kwh) {
        hourlyUsage.setCommunityProduced(hourlyUsage.getCommunityProduced() + kwh);
    }

    private void addUsedEnergy(HourlyUsage hourlyUsage, double kwh) {
        double availableCommunityEnergy = hourlyUsage.getCommunityProduced() - hourlyUsage.getCommunityUsed();
        double communityPart = Math.min(kwh, Math.max(availableCommunityEnergy, 0));
        double gridPart = kwh - communityPart;

        hourlyUsage.setCommunityUsed(hourlyUsage.getCommunityUsed() + communityPart);
        hourlyUsage.setGridUsed(hourlyUsage.getGridUsed() + gridPart);
    }

}
