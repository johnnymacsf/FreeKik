package com.FreeKik.server.Handlers;

import com.FreeKik.server.API.OddsData;
import com.FreeKik.server.models.Odds;
import com.FreeKik.server.models.OddsBook;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class OddsHandler {
    public static OddsBook getBook(String matchId){
        OddsBook oddsBook = new OddsBook();
        JsonArray arr = OddsData.getMatchOdds(matchId);
        Gson gson = new Gson();
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
                            odds.addOuctome(outcomeObj.get("name").toString(), outcomeObj.get("price").toString());
                        }
                    }
                    oddsBook.addOdds(obj.get("title").toString(), odds);
                }
            }
        }
        return oddsBook;
    }
}
