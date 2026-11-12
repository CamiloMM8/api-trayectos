package com.example.Proyecto.APIs.GoogleMaps;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Geocoder {

    private static final String PLACES_URL = "https://places.googleapis.com/v1/places:searchText";
    private static final String API_KEY = "AIzaSyAvqm37bq1sw_ZWcCM-Nb4GMiEgYoDnvHk";

    public String GeocodeSync(String query) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode body = mapper.createObjectNode();
        body.put("textQuery", query);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PLACES_URL))
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", "application/json")
                .header("X-Goog-Api-Key", API_KEY)
                .header("X-Goog-FieldMask", "places.location")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String getLatLng(String query) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        String response = this.GeocodeSync(query);
        JsonNode responseJsonNode = mapper.readTree(response);
        JsonNode places = responseJsonNode.get("places");
        if (places == null || !places.isArray() || places.isEmpty() || places.get(0).get("location") == null) {
            throw new IOException("Google Maps no devolvio coordenadas para: " + query + " respuesta=" + response);
        }
        JsonNode location = places.get(0).get("location");
        String latitud = location.get("latitude").asText();
        String longitud = location.get("longitude").asText();
        return latitud + "," + longitud;
    }
}
