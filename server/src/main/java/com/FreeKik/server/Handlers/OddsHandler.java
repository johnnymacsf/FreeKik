package com.FreeKik.server.Handlers;

import com.FreeKik.server.API.OddsData;
import com.FreeKik.server.models.Odds;
import com.FreeKik.server.models.OddsBook;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class OddsHandler {
    public static OddsBook getBook(String matchId){
        OddsBook oddsBook = new OddsBook();
        JsonArray arr = OddsData.getMatchOdds(matchId);
        for(JsonElement element : arr){
            JsonObject obj = element.getAsJsonObject();
            if (obj.has("markets")) {
                JsonArray markets = obj.getAsJsonArray("markets");
                for (JsonElement marketElement : markets) {
                    JsonObject marketObj = marketElement.getAsJsonObject();
                    Odds odds = new Odds(marketObj.get("key").toString(), obj.get("last_update").toString());
                    if (marketObj.has("outcomes")) {
                        JsonArray outcomes = marketObj.getAsJsonArray("outcomes");
                        for (JsonElement outcome : outcomes) {
                            JsonObject outcomeObj = outcome.getAsJsonObject();
                            String name = outcomeObj.get("name").getAsString();
                            Double price = outcomeObj.get("price").getAsDouble();
                            odds.addOutcome(name, price);
                        }
                    }
                    oddsBook.addOdds(obj.get("title").toString(), odds);
                }
            }
        }
        return oddsBook;
    }
}
