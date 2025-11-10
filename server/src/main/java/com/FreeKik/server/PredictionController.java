package com.FreeKik.server;

import com.FreeKik.server.models.Prediction;
import com.FreeKik.server.service.PredictionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prediction")
public class PredictionController {
    private final PredictionService predictionService;

    public PredictionController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @GetMapping("/find/user/{id}")
    public ResponseEntity<List<Prediction>> getAllUserPredictions(@PathVariable("id") Long userId) {
        List<Prediction> predictions = predictionService.findPredictionsByUserId(userId);
        return new ResponseEntity<>(predictions, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Prediction> getPredictionById(@PathVariable("id") Long predictionId) {
        Prediction prediction = predictionService.findPredictionByPredictionId(predictionId);
        return new ResponseEntity<>(prediction, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Prediction> addPrediction(@RequestBody Prediction prediction) {
        Prediction newPrediction = predictionService.addPrediction(prediction);
        return new ResponseEntity<>(newPrediction, HttpStatus.CREATED);
    }

    @PutMapping("/update/match/{id}")
    public ResponseEntity<List<Prediction>> updateByMatch(@PathVariable("id") String matchId, @RequestBody String outcome){
        List<Prediction> predictions = predictionService.findPredictionsByMatchId(matchId);
        predictions.forEach((p) -> p.updateFinalResult(outcome));
        return new ResponseEntity<>(predictions, HttpStatus.OK);
    }
}