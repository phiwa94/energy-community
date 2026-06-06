package com.technikum.energy_community_rest.dto;

import java.time.LocalDateTime;

public class UsageDataDto {

    private Long id;
    private LocalDateTime timestamp;
    private double community_produced;
    private double community_used;
    private double grid_used;

    public UsageDataDto() {
    }

    public UsageDataDto(Long id, LocalDateTime timestamp, double community_produced, double community_used, double grid_used) {
        this.id = id;
        this.timestamp = timestamp;
        this.community_produced = community_produced;
        this.community_used = community_used;
        this.grid_used = grid_used;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public double getCommunity_produced() {
        return community_produced;
    }

    public double getCommunity_used() {
        return community_used;
    }

    public double getGrid_used() {
        return grid_used;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setCommunity_produced(double community_produced) {
        this.community_produced = community_produced;
    }

    public void setCommunity_used(double community_used) {
        this.community_used = community_used;
    }

    public void setGrid_used(double grid_used) {
        this.grid_used = grid_used;
    }
}