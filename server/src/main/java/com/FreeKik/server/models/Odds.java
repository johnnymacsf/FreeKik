package com.FreeKik.server.models;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.HashMap;

public class Odds implements Serializable {
    @SerializedName("key")
    private String betType;
    @SerializedName("last_update")
    private String lastUpdate;
    private HashMap<String, Double> outcomes;

    public Odds(String betType, String lastupdate){
        this.betType = betType;
        this.lastUpdate = lastupdate;
        this.outcomes = new HashMap<>();
    }

    public void addOuctome(String name, String price){
        this.outcomes.put(name, Double.parseDouble(price));
    }

    public Double getOutcomeOdds(String outcome){
        return outcomes.get(outcome);
    }

    public static class OddsDeserializer implements JsonDeserializer<Odds>{
        @Override
        public Odds deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject obj = jsonElement.getAsJsonObject();
            Gson gson = new Gson();
            Odds odds = gson.fromJson(obj, Odds.class);

            if (obj.has("outcomes")) {
                JsonArray outcomes = obj.getAsJsonObject("outcomes").getAsJsonArray();
                for(JsonElement element : outcomes){
                    JsonObject outcomeObj = element.getAsJsonObject();
                    odds.addOuctome(outcomeObj.get("name").toString(), outcomeObj.get("price").toString());
                }
            }
            return odds;
        }
    }
}
