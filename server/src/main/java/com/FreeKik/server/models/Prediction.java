package com.FreeKik.server.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Prediction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long predictionId;

    private String homeTeam;
    private String awayTeam;
    private String finalResult;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    private String resultPrediction;
    private String predictionOdds;
    private Long pointsBet;
    private Boolean correctPrediction = false;
    private Long pointsResult = 0L;

    public Prediction() {}

    public Prediction(Long predictionId, User user, Match match, String result_prediction, String prediction_odds, Long pointsBet) {
        this.predictionId = predictionId;
        this.user = user;
        this.match = match;
        this.resultPrediction = result_prediction;
        this.predictionOdds = prediction_odds;
        this.pointsBet = pointsBet;
    }

    public Long getPredictionId() {
        return predictionId;
    }

    public void setPredictionId(Long predictionId) {
        this.predictionId = predictionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public String getResult_prediction() {
        return resultPrediction;
    }

    public void setResult_prediction(String result_prediction) {
        this.resultPrediction = result_prediction;
    }

    public String getPrediction_odds() {
        return predictionOdds;
    }

    public void setPrediction_odds(String prediction_odds) {
        this.predictionOdds = prediction_odds;
    }

    public Long getPointsBet() {
        return pointsBet;
    }

    public void setPointsBet(Long pointsBet) {
        this.pointsBet = pointsBet;
    }

    public Boolean getCorrectPrediction() {
        return correctPrediction;
    }

    public void setCorrectPrediction(Boolean correctPrediction) {
        this.correctPrediction = correctPrediction;
    }

    public Long getPointsResult() {
        return pointsResult;
    }

    public void setPointsResult(Long pointsResult) {
        this.pointsResult = pointsResult;
    }

    @Override
    public String toString() {
        return "Prediction{" +
                "predictionId=" + predictionId +
                ", user=" + user +
                ", match=" + match +
                ", result_prediction='" + resultPrediction + '\'' +
                ", prediction_odds='" + predictionOdds + '\'' +
                ", pointsBet=" + pointsBet +
                ", correctPrediction=" + correctPrediction +
                ", pointsResult=" + pointsResult +
                '}';
    }
}
