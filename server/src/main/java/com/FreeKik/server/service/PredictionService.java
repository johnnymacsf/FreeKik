package com.FreeKik.server.service;

import com.FreeKik.server.models.Prediction;
import com.FreeKik.server.repo.PredictionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PredictionService {
    private final PredictionRepo predictionRepo;

    @Autowired
    public PredictionService(PredictionRepo predRepo) {
        this.predictionRepo = predRepo;
    }

    public Prediction addPrediction(Prediction prediction){
        return predictionRepo.save(prediction);
    }

    public Prediction updatePrediction(Long id, Prediction update) {
        Prediction prediction = findPredictionByPredictionId(id);
        prediction = update;
        return predictionRepo.save(prediction);
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
