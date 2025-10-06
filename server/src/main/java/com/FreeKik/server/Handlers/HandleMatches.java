package com.FreeKik.server.Handlers;

import com.FreeKik.server.API.OddsData;
import com.FreeKik.server.models.Match;
import com.FreeKik.server.models.MatchMap;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.util.HashMap;

public class HandleMatches {
    public MatchMap getMatchMap(){
        JsonObject object = OddsData.getAllOddsData();
        MatchMap mp = new MatchMap();
        Gson gson = new Gson();
        JsonArray arr = object.getAsJsonArray();

        for(JsonElement element : arr){
            JsonObject matchObj = element.getAsJsonObject();
            Match match = gson.fromJson(matchObj, Match.class);
            mp.addMatch(matchObj.get("id").toString(), match);
        }

        return mp;
    }

    public Match updateScore(Match match){
        JsonObject object = OddsData.getMatchScore(match.getMatchId());

        LocalDate gameDate = LocalDate.parse(object.get("commence_time").toString());
        if(gameDate.isBefore(LocalDate.now())) { return match; }

        JsonArray scores = object.get("scores").getAsJsonArray();
        HashMap<String, Integer> map = new HashMap<>();

        for(JsonElement e : scores){
            JsonObject obj = e.getAsJsonObject();
            map.put(obj.get("name").toString(), obj.get("score").getAsInt());
        }

        int homeScore = map.get(match.getHomeTeam());
        match.setHomeScore(homeScore);
        int awayScore = map.get(match.getAwayTeam());
        match.setAwayScore(awayScore);

        if(object.get("completed").getAsBoolean()){
            if(homeScore == awayScore){
                match.setFinalResult("DRAW");
            }
            else if(homeScore > awayScore){
                match.setFinalResult("HOME");
            }
            else {
                match.setFinalResult("AWAY");
            }
        }

        return match;
    }
}
