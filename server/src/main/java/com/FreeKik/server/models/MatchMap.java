package com.FreeKik.server.models;

import java.util.HashMap;

public class MatchMap {
    private HashMap<String, Match> matches;

    public MatchMap(){
        this.matches = new HashMap<>();
    }

    public void addMatch(String matchId, Match match){
        matches.put(matchId, match);
    }
}
