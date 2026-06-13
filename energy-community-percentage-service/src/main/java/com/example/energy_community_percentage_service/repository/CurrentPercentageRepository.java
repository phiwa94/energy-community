package com.example.energy_community_percentage_service.repository;

import com.example.energy_community_percentage_service.model.CurrentPercentage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CurrentPercentageRepository extends JpaRepository<CurrentPercentage, Long> {
    Optional<CurrentPercentage> findByHourStart(LocalDateTime hourStart);
}
