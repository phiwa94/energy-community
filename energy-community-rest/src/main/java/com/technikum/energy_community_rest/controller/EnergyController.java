package com.technikum.energy_community_rest.controller;

import com.technikum.energy_community_rest.dto.PercentageDataDto;
import com.technikum.energy_community_rest.dto.UsageDataDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/energy")
public class EnergyController {
    private List<PercentageDataDto> listPercentageData = new ArrayList<>(List.of(
            new PercentageDataDto(1, LocalDateTime.parse("2026-03-27T14:00:00"), 100.0, 100.0)
    ));
    private List<UsageDataDto> listUsageData = new ArrayList<>(List.of(
            new UsageDataDto(1, LocalDateTime.parse("2026-03-27T13:00:00"), 100.0, 100.0, 100.0),
            new UsageDataDto(2, LocalDateTime.parse("2026-03-27T14:00:00"), 100.0, 100.0, 100.0)
    ));

    @GetMapping("/current")
    public PercentageDataDto getCurrentEnergy() {
        return listPercentageData.get(listPercentageData.size() - 1);
    }

    @GetMapping("/historical")
    public List<UsageDataDto> getHistoricalEnergy(
            @RequestParam(name = "start") String start,
            @RequestParam(name = "end") String end
    ) {
        if (start != null || end != null) {
            LocalDateTime startDate = LocalDateTime.parse(start);
            LocalDateTime endDate = LocalDateTime.parse(end);

            return listUsageData.stream()
                    .filter(usageDataDto -> !usageDataDto.getTimestamp().isBefore(startDate) && !usageDataDto.getTimestamp().isAfter(endDate))
                    .toList();
        }
        return null;
    }
}


