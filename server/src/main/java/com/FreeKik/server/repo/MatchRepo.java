package com.FreeKik.server.repo;

import com.FreeKik.server.models.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatchRepo extends JpaRepository<Match, Long> {
    void deleteMatchByMatchId(String matchId);

    Optional<Match> findMatchByMatchId(String matchId);
}
