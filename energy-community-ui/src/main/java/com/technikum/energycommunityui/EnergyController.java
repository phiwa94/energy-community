package com.technikum.energycommunityui;

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
    @FXML
    private Label currentDataLabel;

    @FXML
    private Label historicalDataLabel;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private Spinner startHourSpinner;

    @FXML
    private Spinner endHourSpinner;

    @FXML
    protected void onCurrentDataClick() {
        try {
            String url = "http://localhost:8080/energy/current";
            HttpRequest getRequest = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

            currentDataLabel.setText(response.body());
        } catch (Exception exception) {
            System.err.println("Something went wrong during get" + exception.getMessage());
        }
    }

    @FXML
    protected void onHistoricalDataClick() {
        if (startDatePicker.getValue() == null || endDatePicker.getValue() == null) {
            historicalDataLabel.setText("Please select both a start and end date.");
            return;
        }

        LocalDateTime startDateTime = startDatePicker.getValue()
                .atTime((Integer) startHourSpinner.getValue(), 0);

        LocalDateTime endDateTime = endDatePicker.getValue()
                .atTime((Integer) endHourSpinner.getValue(), 0);

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

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                if (response.body().equals("[]")) {
                    historicalDataLabel.setText("No data found for selected time range.");
                } else {
                    historicalDataLabel.setText(response.body());
                }
            } else {
                historicalDataLabel.setText("Server error: " + response.statusCode());
            }

        } catch (Exception exception) {
            historicalDataLabel.setText("Could not load historical data. Is the REST API running?");
        }
    }

    /*@FXML
    protected void onHistoricalDataClick() {
        if (startDatePicker.getValue() == null || startHourSpinner == null || endDatePicker.getValue() == null || endHourSpinner == null) {
            historicalDataLabel.setText("Please fill in all required fields.");
        } else {
            String queryForStartDatePicker = startDatePicker.getValue().toString();
            String queryForEndDatePicker = endDatePicker.getValue().toString();
            String queryForStartHourSpinner = startHourSpinner.getValue().toString();
            String queryForEndHourSpinner = endHourSpinner.getValue().toString();

            try {
                String url = "http://localhost:8080/energy/historical?" + "start=" + queryForStartDatePicker + "T" + queryForStartHourSpinner + ":00:00" +
                        "&" + "end=" + queryForEndDatePicker + "T" + queryForEndHourSpinner + ":00:00";
                HttpRequest getRequest = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
                HttpClient client = HttpClient.newBuilder().build();

                HttpResponse<String> response = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

                historicalDataLabel.setText(response.body());
            } catch (Exception exception) {
                System.err.println("Something went wrong during get" + exception.getMessage());
            }
        }
    } */
}