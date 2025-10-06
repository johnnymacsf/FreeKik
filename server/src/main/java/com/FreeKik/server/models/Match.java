package com.FreeKik.server.models;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;
import com.google.gson.*;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.time.LocalDate;

@Entity
public class Match implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long key; //primary key id that we generate in the database table

    @Column(unique = true, nullable = false)
    @SerializedName("id")
    private String matchId; //matchId from the API
    @SerializedName("home_team")
    private String homeTeam;
    @SerializedName("away_team")
    private String awayTeam;
    private String finalResult = ""; //default is blank for now and we update it when the game finishes
    @SerializedName("commence_time")
    private LocalDate matchDate;

    private Book book;

    public Match(String matchId, String homeTeam, String awayTeam, String finalResult, LocalDate matchDate) {
        this.matchId = matchId;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.finalResult = finalResult;
        this.matchDate = matchDate;
        this.book = new Book();
    }

    public Match(Long key, String matchId, String homeTeam, String awayTeam, LocalDate matchDate) {
        this.key = key;
        this.matchId = matchId;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.matchDate = matchDate;
        this.book = new Book();
    }

    public Long getKey() {
        return key;
    }

    public String getMatchId() {
        return matchId;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public String getFinalResult() {
        return finalResult;
    }

    public LocalDate getMatchDate() {
        return matchDate;
    }

    public void setBook(Book book){
        this.book = book;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + key +
                ", matchId='" + matchId + '\'' +
                ", homeTeam='" + homeTeam + '\'' +
                ", awayTeam='" + awayTeam + '\'' +
                ", finalResult='" + finalResult + '\'' +
                ", matchDate=" + matchDate +
                '}';
    }

    public static class MatchDeserializer implements JsonDeserializer<Match> {
        @Override
        public Match deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject obj = jsonElement.getAsJsonObject();
            Gson gson = new Gson();
            Match match = gson.fromJson(obj, Match.class);

            if (obj.has("bookmakers")) {
                Book book = gson.fromJson(obj.getAsJsonObject("bookmakers"), Book.class);
                match.setBook(book);
            }
            return match;
        }
    }
}
