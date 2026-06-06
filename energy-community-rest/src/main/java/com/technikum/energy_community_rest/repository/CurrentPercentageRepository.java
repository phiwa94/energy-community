package com.technikum.energy_community_rest.repository;

import com.technikum.energy_community_rest.model.CurrentPercentage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrentPercentageRepository extends JpaRepository<CurrentPercentage, Long> {

    Optional<CurrentPercentage> findTopByOrderByHourStartDesc();
}