package com.FreeKik.server.Handlers;

import com.FreeKik.server.API.OddsData;
import com.FreeKik.server.models.OddsBook;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class OddsHandler {
    public static OddsBook getBook(String matchId){
        JsonObject obj = OddsData.getMatchOdds(matchId);
        Gson gson = new Gson();
        return gson.fromJson(obj.getAsJsonObject("bookmakers"), OddsBook.class);
    }
}
