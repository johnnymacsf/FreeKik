package com.FreeKik.server.service;

import com.FreeKik.server.models.Match;
import com.FreeKik.server.repo.MatchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatchService {
    private final MatchRepo matchRepo;

    @Autowired
    public MatchService(MatchRepo matchRepo) {
        this.matchRepo = matchRepo;
    }

    public Match addMatch(Match match){
        return matchRepo.save(match);
    }

    public List<Match> findAllMatches(){
        return matchRepo.findAll();
    }

    public Match updateMatch(Match match){
        return matchRepo.save(match);
    }

    public void deleteMatch(String id){ matchRepo.deleteMatchByMatchId(id); }

    public Match findMatchById(String id){
        return matchRepo.findMatchByMatchId(id).orElseThrow(() -> new IllegalArgumentException("Match with id " + id + " not found"));
    }

}
