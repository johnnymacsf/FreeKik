package com.FreeKik.server.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.*;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.HashMap;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OddsBook implements Serializable {
    private HashMap<String, Odds> book;

    public OddsBook(){
        this.book = new HashMap<>();
    }

    public HashMap<String, Double> getAvg(String home, String away){
        HashMap<String, Double> map = new HashMap<>();
        double homeOdds = 0.0;
        double awayOdds = 0.0;
        double drawOdds = 0.0;
        for(Odds odds : this.book.values()){
            homeOdds += odds.getOutcomeOdds(home);
            awayOdds += odds.getOutcomeOdds(away);
            drawOdds += odds.getOutcomeOdds("Draw");
        }
        map.put(home, homeOdds/this.book.size());
        map.put(away, awayOdds/this.book.size());
        map.put("Draw", drawOdds/this.book.size());
        return map;
    }

    public void addOdds(String bookmaker, Odds odds){
        this.book.put(bookmaker, odds);
    }

    public HashMap<String, Double> getBookmakerOdds(String bookmaker){
        return book.get(bookmaker).getOutcomes();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        book.forEach((k, v) -> sb.append("Bookmaker: " + k + ": " + v.toString() + " "));
        return sb.toString();
    }

    public HashMap<String, Odds> getBook() {
        return book;
    }

    public static class BookDeserializer implements JsonDeserializer<OddsBook> {
        @Override
        public OddsBook deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonArray bookmakers = jsonElement.getAsJsonArray();
            Gson gson = new Gson();
            OddsBook book = new OddsBook();
            for(JsonElement element : bookmakers){
                JsonObject oddsObj = element.getAsJsonObject();
                book.addOdds(oddsObj.get("title").toString(), gson.fromJson(oddsObj.getAsJsonObject("markets"), Odds.class));
            }
            return book;
        }
    }
}
