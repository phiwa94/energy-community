package com.technikum.energycommunityui;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

public class EnergyController {

    private final HttpClient client = HttpClient.newBuilder().build();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @FXML
    private Label currentDataLabel;

    @FXML
    private Label historicalDataLabel;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private Spinner<Integer> startHourSpinner;

    @FXML
    private Spinner<Integer> endHourSpinner;

    @FXML
    protected void onCurrentDataClick() {
        try {
            String url = "http://localhost:8080/energy/current";

            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(getRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                currentDataLabel.setText(formatCurrentData(response.body()));
            } else if (response.statusCode() == 404) {
                currentDataLabel.setText("No current percentage data found yet.");
            } else {
                currentDataLabel.setText("Server error: " + response.statusCode());
            }

        } catch (Exception exception) {
            currentDataLabel.setText("Could not load current data. Is the REST API running?");
        }
    }

    @FXML
    protected void onHistoricalDataClick() {
        if (startDatePicker.getValue() == null || endDatePicker.getValue() == null) {
            historicalDataLabel.setText("Please select both a start and end date.");
            return;
        }

        LocalDateTime startDateTime = startDatePicker.getValue()
                .atTime(startHourSpinner.getValue(), 0);

        LocalDateTime endDateTime = endDatePicker.getValue()
                .atTime(endHourSpinner.getValue(), 0);

        if (startDateTime.isAfter(endDateTime)) {
            historicalDataLabel.setText("Start date/time must be before end date/time.");
            return;
        }

        try {
            String url = "http://localhost:8080/energy/historical?start="
                    + startDateTime + "&end=" + endDateTime;

            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(getRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                if (response.body().equals("[]")) {
                    historicalDataLabel.setText("No data found for selected time range.");
                } else {
                    historicalDataLabel.setText(formatHistoricalData(response.body()));
                }
            } else {
                historicalDataLabel.setText("Server error: " + response.statusCode());
            }

        } catch (Exception exception) {
            historicalDataLabel.setText("Could not load historical data. Is the REST API running?");
        }
    }

    private String formatCurrentData(String json) throws Exception {
        JsonNode node = objectMapper.readTree(json);

        return "Current energy distribution\n"
                + "Hour: " + node.get("timestamp").asText() + "\n"
                + "Community depleted: " + node.get("community_depleted").asDouble() + "%\n"
                + "Grid portion: " + node.get("grid_portion").asDouble() + "%";
    }

    private String formatHistoricalData(String json) throws Exception {
        JsonNode array = objectMapper.readTree(json);

        StringBuilder builder = new StringBuilder("Historical usage data\n");

        for (JsonNode node : array) {
            builder.append("\n")
                    .append("Hour: ").append(node.get("timestamp").asText()).append("\n")
                    .append("Community produced: ").append(node.get("community_produced").asDouble()).append(" kWh\n")
                    .append("Community used: ").append(node.get("community_used").asDouble()).append(" kWh\n")
                    .append("Grid used: ").append(node.get("grid_used").asDouble()).append(" kWh\n");
        }

        return builder.toString();
    }
}