package com.FreeKik.server.API;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class OddsData {
    private static final String apiKey = "ca81cffb7dd2d88f9770009d6fb10e53";
    private static final String sportsKey = "soccer_epl";
    private static final String regions = "us";
    private static final String markets = "h2h";

    public static JsonArray getAllOddsData() {
        HttpResponse<String> response = null;
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.the-odds-api.com/v4/sports/" + sportsKey
                            + "/odds/?apiKey=" + apiKey
                            + "&regions=" + regions
                            + "&markets=" + markets))
                    .build();

            response = client.send(request, BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(response.body(), JsonArray.class);
        return jsonArray;
    }

    public static JsonArray getUpcomingMatches() {
        HttpResponse<String> response = null;
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.the-odds-api.com/v4/sports/" + sportsKey
                            + "/events?apiKey=" + apiKey))
                    .build();

            response = client.send(request, BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(response.body(), JsonArray.class);
        return jsonArray;
    }

    public static JsonObject getMatchScore(String matchId){
        HttpResponse<String> response = null;
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.the-odds-api.com/v4/sports/" + sportsKey
                            + "/scores/?apiKey=" + apiKey
                            + "&eventIds=" + matchId))
                    .build();

            response = client.send(request, BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
        return jsonObject;
    }

    public static JsonArray getMatchOdds(String matchId){
        HttpResponse<String> response = null;
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.the-odds-api.com/v4/sports/" + sportsKey
                            + "/odds/?apiKey=" + apiKey
                            + "&regions=" + regions
                            + "&markets=" + markets
                            + "&eventIds=" + matchId))
                    .build();

            response = client.send(request, BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        JsonElement element = gson.fromJson(response.body(), JsonElement.class);
        if (element.isJsonArray()) {
            JsonArray arr = element.getAsJsonArray();
            if (arr.size() > 0) {
                JsonObject firstEvent = arr.get(0).getAsJsonObject();
                if (firstEvent.has("bookmakers")) {
                    return firstEvent.get("bookmakers").getAsJsonArray();
                }
            }
            return new JsonArray(); // empty if no bookmakers
        } else if (element.isJsonObject()) {
            JsonObject obj = element.getAsJsonObject();
            // Optional: log API error message
            if (obj.has("msg")) {
                System.out.println("API returned message: " + obj.get("msg").getAsString());
            }
            return new JsonArray(); // return empty if API returned an object
        } else {
            return new JsonArray(); // fallback
        }
    }
}
