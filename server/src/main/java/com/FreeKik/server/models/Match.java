package com.FreeKik.server.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Match implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //primary key id that we generate in the database table

    @Column(unique = true, nullable = false)
    private String matchId; //matchId from the API
    private String homeTeam;
    private String awayTeam;
    private String finalResult = ""; //default is blank for now and we update it when the game finishes
    private LocalDate matchDate;
    private String homeWinOdds;
    private String awayWinOdds;
    private String drawOdds;

    public Match(){}

    public Match(String matchId, String homeTeam, String awayTeam, String finalResult, LocalDate matchDate, String homeWinOdds, String awayWinOdds, String drawOdds) {
        this.matchId = matchId;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.finalResult = finalResult;
        this.matchDate = matchDate;
        this.homeWinOdds = homeWinOdds;
        this.awayWinOdds = awayWinOdds;
        this.drawOdds = drawOdds;
    }

    public Match(Long id, String matchId, String homeTeam, String awayTeam, LocalDate matchDate, String homeWinOdds, String awayWinOdds, String drawOdds) {
        this.id = id;
        this.matchId = matchId;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.matchDate = matchDate;
        this.homeWinOdds = homeWinOdds;
        this.awayWinOdds = awayWinOdds;
        this.drawOdds = drawOdds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getFinalResult() {
        return finalResult;
    }

    public void setFinalResult(String finalResult) {
        this.finalResult = finalResult;
    }

    public LocalDate getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDate matchDate) {
        this.matchDate = matchDate;
    }

    public String getAwayWinOdds() {
        return awayWinOdds;
    }

    public void setAwayWinOdds(String awayWinOdds) {
        this.awayWinOdds = awayWinOdds;
    }

    public String getHomeWinOdds() {
        return homeWinOdds;
    }

    public void setHomeWinOdds(String homeWinOdds) {
        this.homeWinOdds = homeWinOdds;
    }

    public String getDrawOdds() {
        return drawOdds;
    }

    public void setDrawOdds(String drawOdds) {
        this.drawOdds = drawOdds;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", matchId='" + matchId + '\'' +
                ", homeTeam='" + homeTeam + '\'' +
                ", awayTeam='" + awayTeam + '\'' +
                ", finalResult='" + finalResult + '\'' +
                ", matchDate=" + matchDate +
                ", homeWinOdds='" + homeWinOdds + '\'' +
                ", awayWinOdds='" + awayWinOdds + '\'' +
                ", drawOdds='" + drawOdds + '\'' +
                '}';
    }
}
