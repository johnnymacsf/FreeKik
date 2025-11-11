package com.FreeKik.server.repo;

import com.FreeKik.server.models.Club;
import com.FreeKik.server.models.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClubRepo extends JpaRepository<Club, String> {
    void deleteClubByClubName(String clubName);

    Optional<Club> findClubByClubName(String clubName);
}
