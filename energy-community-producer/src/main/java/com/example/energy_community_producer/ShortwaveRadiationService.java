package com.example.energy_community_producer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ShortwaveRadiationService {
    private double shortwaveRadiation;

    public ShortwaveRadiationService() {}

    public double getShortwaveRadiation() {

        try {
            String url = "https://api.open-meteo.com/v1/forecast?latitude=48.210033&longitude=16.363449&current=shortwave_radiation&timezone=Europe%2FBerlin";

            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

            String after = response.body().split("\"shortwave_radiation\":")[2];
            this.shortwaveRadiation = Double.parseDouble(after.split("[}]")[0].trim());

            /* error handling tbd
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                if (response.body().equals("[]")) {
                    historicalDataLabel.setText("No data found for selected time range.");
                } else {
                    historicalDataLabel.setText(response.body());
                }
            } else {
                historicalDataLabel.setText("Server error: " + response.statusCode());
            }
             */

        } catch (Exception exception) {
            System.out.println("Error with receiving weather API data - " + exception);
        }

        return shortwaveRadiation;
    }

    public int getShortwaveRadiationFactor() {
        return (int) Math.round(this.getShortwaveRadiation() / 1000);
    }
}
