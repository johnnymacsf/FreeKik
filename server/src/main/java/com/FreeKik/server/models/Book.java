package com.FreeKik.server.models;

import com.google.gson.*;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.HashMap;

public class Book implements Serializable {
    private HashMap<String, Odds> book;

    public Book(){
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

    public static class BookDeserializer implements JsonDeserializer<Book> {
        @Override
        public Book deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonArray bookmakers = jsonElement.getAsJsonArray();
            Gson gson = new Gson();
            Book book = new Book();
            for(JsonElement element : bookmakers){
                JsonObject oddsObj = element.getAsJsonObject();
                book.addOdds(oddsObj.get("title").toString(), gson.fromJson(oddsObj.getAsJsonObject("markets"), Odds.class));
            }
            return book;
        }
    }
}
