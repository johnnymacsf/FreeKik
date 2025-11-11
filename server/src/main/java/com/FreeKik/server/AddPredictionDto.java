package com.FreeKik.server;

public class AddPredictionDto {
    private String matchId;
    private String resultPrediction;
    private Long pointsBet;
    private String username;

    public AddPredictionDto(){

    }

    public AddPredictionDto(String matchId, String resultPrediction, Long pointsBet, String username) {
        this.matchId = matchId;
        this.resultPrediction = resultPrediction;
        this.pointsBet = pointsBet;
        this.username = username;
    }

    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getResultPrediction() {
        return resultPrediction;
    }

    public void setResultPrediction(String resultPrediction) {
        this.resultPrediction = resultPrediction;
    }

    public Long getPointsBet() {
        return pointsBet;
    }

    public void setPointsBet(Long pointsBet) {
        this.pointsBet = pointsBet;
    }
}
