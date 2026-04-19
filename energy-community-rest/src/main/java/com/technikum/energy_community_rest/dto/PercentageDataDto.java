package com.technikum.energy_community_rest.dto;

import java.time.LocalDateTime;

public class PercentageDataDto {
    private int id;
    private LocalDateTime timestamp;
    private double community_depleted;
    private double grid_portion;

    public PercentageDataDto() {
    }

    public PercentageDataDto(int id, LocalDateTime timestamp, double community_depleted, double grid_portion) {
        this.id = id;
        this.timestamp = timestamp;
        this.community_depleted = community_depleted;
        this.grid_portion = grid_portion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getCommunity_depleted() {
        return community_depleted;
    }

    public void setCommunity_depleted(double community_depleted) {
        this.community_depleted = community_depleted;
    }

    public double getGrid_portion() {
        return grid_portion;
    }

    public void setGrid_portion(double grid_portion) {
        this.grid_portion = grid_portion;
    }
}
