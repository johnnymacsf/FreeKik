package com.FreeKik.server.Handlers;

import com.FreeKik.server.models.Match;
import com.FreeKik.server.models.MatchMap;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class HandleMatches {
    public MatchMap getMatchMap(JsonObject object){
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

    public MatchMap getMatchMap(String string){
        MatchMap mp = new MatchMap();
        Gson gson = new Gson();
        JsonArray arr = gson.fromJson(string, JsonObject.class).getAsJsonArray();
        for(JsonElement element : arr){
            JsonObject matchObj = element.getAsJsonObject();
            Match match = gson.fromJson(matchObj, Match.class);
            mp.addMatch(matchObj.get("id").toString(), match);
        }

        return mp;
    }
}
