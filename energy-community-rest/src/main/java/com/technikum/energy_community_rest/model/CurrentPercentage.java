package com.technikum.energy_community_rest.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "current_percentage")
public class CurrentPercentage {

    @Id
    private Long id;

    @Column(name = "hour_start", nullable = false, unique = true)
    private LocalDateTime hourStart;

    @Column(name = "community_depleted", nullable = false)
    private double communityDepleted;

    @Column(name = "grid_portion", nullable = false)
    private double gridPortion;

    public CurrentPercentage() {
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getHourStart() {
        return hourStart;
    }

    public double getCommunityDepleted() {
        return communityDepleted;
    }

    public double getGridPortion() {
        return gridPortion;
    }
}