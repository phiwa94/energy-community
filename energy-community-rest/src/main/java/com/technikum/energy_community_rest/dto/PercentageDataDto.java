package com.technikum.energy_community_rest.dto;

import java.time.LocalDateTime;

public class PercentageDataDto {

    private Long id;
    private LocalDateTime timestamp;
    private double community_depleted;
    private double grid_portion;

    public PercentageDataDto() {
    }

    public PercentageDataDto(Long id, LocalDateTime timestamp, double community_depleted, double grid_portion) {
        this.id = id;
        this.timestamp = timestamp;
        this.community_depleted = community_depleted;
        this.grid_portion = grid_portion;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public double getCommunity_depleted() {
        return community_depleted;
    }

    public double getGrid_portion() {
        return grid_portion;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setCommunity_depleted(double community_depleted) {
        this.community_depleted = community_depleted;
    }

    public void setGrid_portion(double grid_portion) {
        this.grid_portion = grid_portion;
    }
}