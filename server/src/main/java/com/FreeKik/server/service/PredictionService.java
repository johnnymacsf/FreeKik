package com.FreeKik.server.service;

import com.FreeKik.server.models.Match;
import com.FreeKik.server.models.Prediction;
import com.FreeKik.server.models.User;
import com.FreeKik.server.repo.PredictionRepo;
import com.FreeKik.server.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PredictionService {
    private final PredictionRepo predictionRepo;
    private final UserRepo userRepo;
    private final MatchService matchService;
    @Autowired
    public PredictionService(PredictionRepo predRepo, UserRepo userRepo, MatchService matchService) {

        this.predictionRepo = predRepo;
        this.userRepo = userRepo;
        this.matchService = matchService;
    }

    public Prediction addPrediction(Prediction prediction, String username){
        User user = userRepo.findUserByUsername(username).orElseThrow(()-> new IllegalArgumentException("User not found with that username"));
        Match match = matchService.findMatchById(prediction.getMatchId());
        prediction.setUser(user);
        prediction.setDate(LocalDateTime.now().toString());
        prediction.setAwayTeam(match.getAwayTeam());
        prediction.setHomeTeam(match.getHomeTeam());

        return predictionRepo.save(prediction);
    }

    public Prediction updatePrediction(Long id, Prediction update) {
        Prediction existingPrediction = findPredictionByPredictionId(id);
        if(update.getResult_prediction() != null){
            existingPrediction.setResult_prediction(update.getResult_prediction());
        }
        if(update.getPointsBet() != null){
            existingPrediction.setPointsBet(update.getPointsBet());
        }
        if(update.getPrediction_odds() != null){
            existingPrediction.setPrediction_odds(update.getPrediction_odds());
        }
        return predictionRepo.save(existingPrediction);
    }

    public void deletePrediction(Long id){ predictionRepo.deletePredictionByPredictionId(id); }

    public List<Prediction> findPredictionsByUserId(Long userId) {
        return predictionRepo.findPredictionsByUser_UserId(userId).orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));
    }
    public Prediction findPredictionByPredictionId(Long predictionId) {
        return predictionRepo.findPredictionByPredictionId(predictionId).orElseThrow(() -> new IllegalArgumentException("Prediction with id " + predictionId + " not found"));
    }
    public List<Prediction> findPredictionsByMatchId(String matchId) {
        return predictionRepo.findPredictionsByMatchId(matchId).orElseThrow(() -> new IllegalArgumentException("No predictions for match " + matchId));
    }
}
