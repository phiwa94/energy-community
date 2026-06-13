package com.example.energy_community_percentage_service.service;

import com.example.energy_community_percentage_service.model.CurrentPercentage;
import com.example.energy_community_percentage_service.model.HourlyUsage;
import com.example.energy_community_percentage_service.repository.CurrentPercentageRepository;
import com.example.energy_community_percentage_service.repository.HourlyUsageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PercentageCalculationService {

    private final HourlyUsageRepository hourlyUsageRepository;
    private final CurrentPercentageRepository currentPercentageRepository;

    public PercentageCalculationService(HourlyUsageRepository hourlyUsageRepository,
                                        CurrentPercentageRepository currentPercentageRepository) {
        this.hourlyUsageRepository = hourlyUsageRepository;
        this.currentPercentageRepository = currentPercentageRepository;
    }

    @Transactional
    public CurrentPercentage calculateAndSave(LocalDateTime hourStart) {
        HourlyUsage hourlyUsage = hourlyUsageRepository
                .findByHourStart(hourStart)
                .orElseThrow(() -> new IllegalArgumentException("No usage data found for hour: " + hourStart));

        double communityDepleted = calculateCommunityDepleted(hourlyUsage);
        double gridPortion = calculateGridPortion(hourlyUsage);

        currentPercentageRepository.deleteAll();
        currentPercentageRepository.flush();

        CurrentPercentage currentPercentage = new CurrentPercentage(hourStart);
        currentPercentage.setCommunityDepleted(communityDepleted);
        currentPercentage.setGridPortion(gridPortion);

        return currentPercentageRepository.save(currentPercentage);
    }

    private double calculateCommunityDepleted(HourlyUsage hourlyUsage) {
        if (hourlyUsage.getCommunityProduced() <= 0) {
            return 0;
        }

        return hourlyUsage.getCommunityUsed() / hourlyUsage.getCommunityProduced() * 100;
    }

    private double calculateGridPortion(HourlyUsage hourlyUsage) {
        double totalUsed = hourlyUsage.getCommunityUsed() + hourlyUsage.getGridUsed();

        if (totalUsed <= 0) {
            return 0;
        }

        return hourlyUsage.getGridUsed() / totalUsed * 100;
    }
}
