package com.example.energy_community_producer;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
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

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                String after = response.body().split("\"shortwave_radiation\":")[2];
                this.shortwaveRadiation = Double.parseDouble(after.split("[}]")[0].trim());
                System.out.println(this.shortwaveRadiation);
            } else if (response.statusCode() == 404) {
                System.out.println("No current percentage data found yet.");
            } else {
                System.out.println("Server error: " + response.statusCode());
            }

        } catch (Exception exception) {
            System.out.println("Error with receiving weather API data - " + exception);
        }

        return shortwaveRadiation;
    }

    public double getShortwaveRadiationFactor() {
        return this.getShortwaveRadiation() / 1000;
    }
}
