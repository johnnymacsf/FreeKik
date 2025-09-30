package com.FreeKik.server.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "app_user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long userId;
    private String username;
    private String email;
    private String password;
    private Long wins = 0L;
    private Long losses = 0L;
    private Long points = 100L;

    @Column(nullable = false)
    private String role = "USER";

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<Prediction> predictions = new ArrayList<>();

    public User() {
    }

    public User(Long userId, String username, String email, String password, Long wins, Long losses, List<Prediction> predictions, Long points) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.wins = wins;
        this.losses = losses;
        this.predictions = predictions;
        this.points = points;
    }

    public String getRole(){
        return role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getWins() {
        return wins;
    }

    public void setWins(Long wins) {
        this.wins = wins;
    }

    public Long getLosses() {
        return losses;
    }

    public void setLosses(Long losses) {
        this.losses = losses;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public List<Prediction> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<Prediction> predictions) {
        this.predictions = predictions;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", wins=" + wins +
                ", losses=" + losses +
                ", points=" + points +
                ", predictions=" + predictions +
                '}';
    }
}
