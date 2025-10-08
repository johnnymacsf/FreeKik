package com.FreeKik.server.models;

import java.util.HashMap;

public class MatchMap {
    private HashMap<String, Match> matches;

    public MatchMap(){
        this.matches = new HashMap<>();
    }

    public HashMap<String, Match> getMatches() {
        return matches;
    }

    public void addMatch(String matchId, Match match){
        matches.put(matchId, match);
    }

    public void merge(MatchMap otherMap){
        otherMap.getMatches().forEach(this::addMatch);
    }
}
