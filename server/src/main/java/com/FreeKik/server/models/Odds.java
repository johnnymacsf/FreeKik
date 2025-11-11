package com.FreeKik.server.models;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.*;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.HashMap;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Odds implements Serializable {
    @SerializedName("key")
    private String betType;
    @SerializedName("last_update")
    private String lastUpdate;
    private HashMap<String, Double> outcomes;

    public Odds(){
    }

    public Odds(String betType, String lastupdate){
        this.betType = betType;
        this.lastUpdate = lastupdate;
        this.outcomes = new HashMap<>();
    }


    public Double getOutcomeOdds(String outcome){
        return outcomes.get(outcome);
    }

    public void setBetType(String betType) {this.betType = betType;}

    public String getBetType(){
        return betType;
    }

    public void setLastUpdate(String lastUpdate) {this.lastUpdate = lastUpdate;}

    public String getLastUpdate(){
        return lastUpdate;
    }

    @JsonAnyGetter
    public HashMap<String, Double> getOutcomes(){
        return outcomes;
    }

    @JsonAnySetter
    public void addOutcome(String name, String price){
        this.outcomes.put(name.replaceAll("[^a-zA-Z(\\s)]", ""), Double.parseDouble(price));
    }

    public void setOutcomes(HashMap<String, Double> outcomes){
        this.outcomes = outcomes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BetType: " + betType +" LastUpdated: " + lastUpdate + " Outcomes: ");
        if(outcomes != null)
            outcomes.forEach((k, v) -> sb.append(k + ": " + v + " "));
        return sb.toString();
    }

    public static class OddsDeserializer implements JsonDeserializer<Odds>{
        @Override
        public Odds deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject obj = jsonElement.getAsJsonObject();
            Gson gson = new Gson();
            Odds odds = gson.fromJson(obj, Odds.class);
            if (obj.has("markets")) {
                JsonArray markets = obj.getAsJsonObject("markets").getAsJsonArray();
                for (JsonElement marketElement : markets) {
                    JsonObject marketObj = marketElement.getAsJsonObject();
                    odds.setBetType(marketObj.get("key").toString());
                    if (marketObj.has("outcomes")) {
                        JsonArray outcomes = marketObj.getAsJsonObject("outcomes").getAsJsonArray();
                        for (JsonElement element : outcomes) {
                            JsonObject outcomeObj = element.getAsJsonObject();
                            odds.addOutcome(outcomeObj.get("name").toString(), outcomeObj.get("price").toString());
                        }
                    }
                }
            }
            return odds;
        }
    }
}