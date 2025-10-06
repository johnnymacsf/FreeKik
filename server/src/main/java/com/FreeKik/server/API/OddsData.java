package com.FreeKik.server.API;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class OddsData {
    public static JsonObject getAllOddsData() {
        HttpResponse<String> response = null;
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.the-odds-api.com/v4/sports/soccer_epl/odds/?apiKey=ca81cffb7dd2d88f9770009d6fb10e53&regions=us&markets=h2h2"))
                    .build();

            response = client.send(request, BodyHandlers.ofString());


            System.out.println(response.statusCode());
            System.out.println(response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
        return jsonObject;
    }
}