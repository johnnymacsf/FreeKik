package com.FreeKik.server.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Prediction implements Comparable<Prediction>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long predictionId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    private String matchId;

    @Column(name="date_made")
    private String dateMade;
    @Column(name="home_team")
    private String homeTeam;
    @Column(name="away_team")
    private String awayTeam;
    private String finalResult;
    private String resultPrediction;
    private Long predictionOdds = 0L;
    private Long pointsBet = 0L;
    private Long pointsResult = 0L;
    private Boolean correctPrediction = false;

    public Prediction() {}

    public Prediction(Long predictionId, User user, String matchId, String date, String result_prediction, String prediction_odds, Long pointsBet) {
        this.predictionId = predictionId;
        this.user = user;
        this.matchId = matchId;
        this.dateMade = date;
        this.resultPrediction = result_prediction;
        this.predictionOdds = Long.parseLong(prediction_odds);
        this.pointsBet = pointsBet;
    }

    public Long getPredictionId() {
        return predictionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public String getMatchId() {
        return matchId;
    }
    public void setMatchId(String matchId){
        this.matchId = matchId;
    }

    public void setAwayTeam(String awayTeam){
        this.awayTeam = awayTeam;
    }
    public void setHomeTeam(String homeTeam){
        this.homeTeam = homeTeam;
    }
    public String getHomeTeam(){
        return homeTeam;
    }
    public String getAwayTeam(){
        return awayTeam;
    }

    public String getResult_prediction() {
        return resultPrediction;
    }

    public void setResult_prediction(String resultPrediction){
        this.resultPrediction = resultPrediction;
    }

    public Long getPrediction_odds() {
        return predictionOdds;
    }

    public void setPrediction_odds(Long predictionOdds){
        this.predictionOdds = predictionOdds;
    }

    public Long getPointsBet() {
        return pointsBet;
    }

    public void setPointsBet(Long pointsBet){
        this.pointsBet = pointsBet;
    }

    public Boolean getCorrectPrediction() {
        return correctPrediction;
    }

    public Long getPointsResult() {
        return pointsResult;
    }

    public String getDate() { return this.dateMade; }

    public void setDate(String dateMade){
        this.dateMade = dateMade;
    }

    public Long getPotentialPayout() {
        pointsResult = pointsBet * predictionOdds;
        return pointsResult;
    }

    public Long payout() {
        if(correctPrediction){
            return getPotentialPayout();
        }
        else {
            return 0L;
        }
    }

    public void updateFinalResult(String result){
        finalResult = result;
        correctPrediction = finalResult.equals(resultPrediction);
    }

    @Override
    public String toString() {
        return "Prediction{" +
                "predictionId= " + predictionId +
                ", user= " + user +
                ", matchId= " + matchId +
                ", result_prediction= " + resultPrediction + '\'' +
                ", prediction_odds= " + predictionOdds + '\'' +
                ", pointsBet= " + pointsBet +
                ", correctPrediction= " + correctPrediction +
                ", pointsResult= " + pointsResult +
                '}';
    }

    @Override
    public int compareTo(Prediction p){
        return p.getDate().compareTo(this.dateMade);
    }
}
