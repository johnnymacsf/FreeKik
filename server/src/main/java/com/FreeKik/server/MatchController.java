package com.FreeKik.server;

import com.FreeKik.server.Handlers.MatchHandler;
import com.FreeKik.server.models.Match;
import com.FreeKik.server.models.MatchMap;
import com.FreeKik.server.models.OddsBook;
import com.FreeKik.server.models.Prediction;
import com.FreeKik.server.service.MatchService;
import com.FreeKik.server.service.PredictionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/match")
public class MatchController {
    private final MatchService matchService;
    private final MatchHandler matchHandler = new MatchHandler();
    private final PredictionService predictionService;

    public MatchController(MatchService matchService, PredictionService predictionService) {
        this.matchService = matchService;
        this.predictionService = predictionService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/findAll")
    public ResponseEntity<List<Match>> getAllMatches() {
        List<Match> matches = matchService.findAllMatches();
        return new ResponseEntity<>(matches, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Match> getMatchById(@PathVariable("id") String id) {
        Match match = matchService.findMatchById(id);
        return new ResponseEntity<>(match, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Match> addMatch(@RequestBody Match match) {
        Match newMatch = matchService.addMatch(match);
        return new ResponseEntity<>(newMatch, HttpStatus.CREATED);
    }

    @PostMapping("/populate")
    public List<ResponseEntity<Match>> addAllMatches() {
        ArrayList<ResponseEntity<Match>> list = new ArrayList<>();
        MatchMap mm = MatchHandler.getMatchMap();
        HashMap<String, Match> hm = mm.getMatches();
        hm.forEach((id, match) -> list.add(addMatch(match)));
        return list;
    }

    @GetMapping("/odds/{id}")
    public ResponseEntity<OddsBook> getMatchOdds(@PathVariable("id") String id){
        Match match = matchService.findMatchById(id);
        MatchHandler.updateBook(match);
        OddsBook book = match.getBook();
        return ResponseEntity.ok(book);
    }

    @GetMapping("/updateScore")
    public ResponseEntity<Match> updateScore(@RequestBody Match match){
        return ResponseEntity.ok(matchHandler.updateScore(match));
    }

    @PutMapping("/finalize/{id}")
    public ResponseEntity<?> finalizeMatch(
            @PathVariable("id") String matchId,
            @RequestBody Map<String, String> body) {

        String finalResult = body.get("finalResult");
        Match match = matchService.findMatchById(matchId);

        match.setFinalResult(finalResult);
        matchService.finalizeMatchResult(match); // <- save without fetching scores

        List<Prediction> updatedPredictions = predictionService.finalizeMatchPredictions(matchId, finalResult);

        return new ResponseEntity<>(updatedPredictions, HttpStatus.OK);
    }
}