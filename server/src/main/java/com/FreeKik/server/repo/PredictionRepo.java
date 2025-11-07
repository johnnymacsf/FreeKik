package com.FreeKik.server.repo;

import com.FreeKik.server.models.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PredictionRepo extends JpaRepository<Prediction, Long> {
    void deletePredictionByPredictionId(Long predictionId);

    Optional<List<Prediction>> findPredictionsByUserId(Long userId);
    Optional<List<Prediction>> findPredictionsByMatchId(Long matchId);
    Optional<Prediction> findPredictionByPredictionId(Long predictionId);
}
