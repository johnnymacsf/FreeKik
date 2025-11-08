package com.FreeKik.server.models;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;
import com.google.gson.*;

import java.io.Serializable;
import java.lang.reflect.Type;
//import java.time.LocalDate;
import java.util.HashMap;

@Entity
public class Match implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long key;

    @Column(unique = true, nullable = false)
    @SerializedName("id")
    private String matchId; //matchId from the API
    @SerializedName("home_team")
    private String homeTeam;
    @SerializedName("away_team")
    private String awayTeam;
    private int homeScore;
    private int awayScore;
    private String finalResult = "";
    @SerializedName("commence_time")
    private String matchDate;

    private OddsBook book;

    public Match(){
        this.book = new OddsBook();
    }

    public Match(String matchId, String homeTeam, String awayTeam, String matchDate) {
        this.matchId = matchId;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.matchDate = matchDate;
        this.book = new OddsBook();
    }

    public Match(Long key, String matchId, String homeTeam, String awayTeam, String matchDate) {
        this.key = key;
        this.matchId = matchId;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.matchDate = matchDate;
        this.book = new OddsBook();
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

    public void setFinalResult(String result) { this.finalResult = result; }

    public void setHomeScore(int score) { this.homeScore = score; }

    public void setAwayScore(int score) { this.awayScore = score; }

    public String getMatchDate() {
        return matchDate;
    }

    public void setBook(OddsBook book) { this.book = book; }

    public OddsBook getBook() {
        return book;
    }

    public HashMap<String, Double> getAvgOdds(){
        return this.book.getAvg(this.homeTeam, this.awayTeam);
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
                ", OddsBook= " + book.toString() +
                '}' + '\n';
    }

    public static class MatchDeserializer implements JsonDeserializer<Match> {
        @Override
        public Match deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject obj = jsonElement.getAsJsonObject();
            Gson gson = new Gson();
            Match match = gson.fromJson(obj, Match.class);

            if (obj.has("bookmakers")) {
                OddsBook book = gson.fromJson(obj.getAsJsonObject("bookmakers"), OddsBook.class);
                match.setBook(book);
            }
            return match;
        }
    }
}
