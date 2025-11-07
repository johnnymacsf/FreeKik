package com.FreeKik.server;

public class UserStatsDto {
    private long points;
    private long wins;
    private long losses;
    private String username;

    public UserStatsDto(String username, long points, long wins, long losses) {
        this.points = points;
        this.wins = wins;
        this.losses = losses;
        this.username = username;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public long getWins() {
        return wins;
    }

    public void setWins(long wins) {
        this.wins = wins;
    }

    public long getLosses() {
        return losses;
    }

    public void setLosses(long losses) {
        this.losses = losses;
    }

    public void setUsername(String username){ this.username = username; }

    public String getUsername(){ return username; }
}
