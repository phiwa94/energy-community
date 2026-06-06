package com.technikum.energy_community_rest.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "usage_data")
public class HourlyUsage {

    @Id
    private Long id;

    @Column(name = "hour_start", nullable = false, unique = true)
    private LocalDateTime hourStart;

    @Column(name = "community_produced", nullable = false)
    private double communityProduced;

    @Column(name = "community_used", nullable = false)
    private double communityUsed;

    @Column(name = "grid_used", nullable = false)
    private double gridUsed;

    public HourlyUsage() {
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getHourStart() {
        return hourStart;
    }

    public double getCommunityProduced() {
        return communityProduced;
    }

    public double getCommunityUsed() {
        return communityUsed;
    }

    public double getGridUsed() {
        return gridUsed;
    }
}