package com.FreeKik.server.repo;

import com.FreeKik.server.models.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatchRepo extends JpaRepository<Match, Long> {
    void deleteMatchByMatchId(Long matchId);

    Optional<Match> findMatchByMatchId(Long matchId);
}
