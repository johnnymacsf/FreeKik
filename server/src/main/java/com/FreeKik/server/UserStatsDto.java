package com.FreeKik.server;

public class UserStatsDto {
    private long points;
    private long wins;
    private long losses;

    public UserStatsDto(long points, long wins, long losses) {
        this.points = points;
        this.wins = wins;
        this.losses = losses;
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
}
