package com.technikum.energy_community_rest.repository;

import com.technikum.energy_community_rest.model.HourlyUsage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface HourlyUsageRepository extends JpaRepository<HourlyUsage, Long> {

    List<HourlyUsage> findByHourStartBetweenOrderByHourStartAsc(
            LocalDateTime start,
            LocalDateTime end
    );
}