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
            homeOdds += odds.getOutcomeOdds(home) != null ? odds.getOutcomeOdds(home) : 0;
            awayOdds += odds.getOutcomeOdds(away) != null ? odds.getOutcomeOdds(away) : 0;
            drawOdds += odds.getOutcomeOdds("Draw") != null ? odds.getOutcomeOdds("Draw") : 0;
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
        public OddsBook deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonArray bookmakers = jsonElement.getAsJsonArray();
            OddsBook book = new OddsBook();

            for (JsonElement elem : bookmakers) {
                JsonObject bookmakerObj = elem.getAsJsonObject();
                String bookmakerName = bookmakerObj.get("title").getAsString();

                Odds odds = new Odds(); // create a new Odds object for this bookmaker

                JsonArray markets = bookmakerObj.getAsJsonArray("markets");
                for (JsonElement marketElem : markets) {
                    JsonObject market = marketElem.getAsJsonObject();
                    if (!"h2h".equals(market.get("key").getAsString())) continue;

                    JsonArray outcomes = market.getAsJsonArray("outcomes");
                    for (JsonElement outcomeElem : outcomes) {
                        JsonObject outcome = outcomeElem.getAsJsonObject();
                        String team = outcome.get("name").getAsString();
                        Double price = outcome.get("price").getAsDouble();
                        odds.addOutcome(team, price);
                    }
                }

                book.addOdds(bookmakerName, odds);
            }

            return book;
        }
    }
}
