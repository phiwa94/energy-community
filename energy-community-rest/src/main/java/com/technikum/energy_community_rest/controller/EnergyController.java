package com.technikum.energy_community_rest.controller;

import com.technikum.energy_community_rest.dto.PercentageDataDto;
import com.technikum.energy_community_rest.dto.UsageDataDto;
import com.technikum.energy_community_rest.model.CurrentPercentage;
import com.technikum.energy_community_rest.model.HourlyUsage;
import com.technikum.energy_community_rest.repository.CurrentPercentageRepository;
import com.technikum.energy_community_rest.repository.HourlyUsageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/energy")
public class EnergyController {

    private final CurrentPercentageRepository currentPercentageRepository;
    private final HourlyUsageRepository hourlyUsageRepository;

    public EnergyController(CurrentPercentageRepository currentPercentageRepository,
                            HourlyUsageRepository hourlyUsageRepository) {
        this.currentPercentageRepository = currentPercentageRepository;
        this.hourlyUsageRepository = hourlyUsageRepository;
    }

    @GetMapping("/current")
    public PercentageDataDto getCurrentEnergy() {
        CurrentPercentage currentPercentage = currentPercentageRepository
                .findTopByOrderByHourStartDesc()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No current percentage data found"
                ));

        return new PercentageDataDto(
                currentPercentage.getId(),
                currentPercentage.getHourStart(),
                currentPercentage.getCommunityDepleted(),
                currentPercentage.getGridPortion()
        );
    }

    @GetMapping("/historical")
    public List<UsageDataDto> getHistoricalEnergy(
            @RequestParam(name = "start") String start,
            @RequestParam(name = "end") String end
    ) {
        LocalDateTime startDate = LocalDateTime.parse(start);
        LocalDateTime endDate = LocalDateTime.parse(end);

        List<HourlyUsage> usageData = hourlyUsageRepository
                .findByHourStartBetweenOrderByHourStartAsc(startDate, endDate);

        return usageData.stream()
                .map(hourlyUsage -> new UsageDataDto(
                        hourlyUsage.getId(),
                        hourlyUsage.getHourStart(),
                        hourlyUsage.getCommunityProduced(),
                        hourlyUsage.getCommunityUsed(),
                        hourlyUsage.getGridUsed()
                ))
                .toList();
    }
}