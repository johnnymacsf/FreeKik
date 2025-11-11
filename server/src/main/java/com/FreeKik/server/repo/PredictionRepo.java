package com.FreeKik.server.repo;

import com.FreeKik.server.models.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PredictionRepo extends JpaRepository<Prediction, Long> {
    void deletePredictionByPredictionId(Long predictionId);

    List<Prediction> findPredictionsByUser_UserId(Long userId);
    Optional<List<Prediction>> findPredictionsByMatchId(String matchId);
    Optional<Prediction> findPredictionByPredictionId(Long predictionId);
}