package com.example.energy_community_usage_service.repository;

import com.example.energy_community_usage_service.model.HourlyUsage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface HourlyUsageRepository extends JpaRepository<HourlyUsage, Long> {
    Optional<HourlyUsage> findByHourStart(LocalDateTime hourStart);
}
