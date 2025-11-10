package com.FreeKik.server.service;

import com.FreeKik.server.models.Club;
import com.FreeKik.server.models.Match;
import com.FreeKik.server.repo.ClubRepo;
import com.FreeKik.server.repo.MatchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ClubService {
    private final ClubRepo clubRepo;

    @Autowired
    public ClubService(ClubRepo clubRepo) {
        this.clubRepo = clubRepo;
    }

    public Club addClub(Club club){ return clubRepo.save(club); }

    public List<Club> findAllClubs(){
        return clubRepo.findAll();
    }

    public Club updateClub(Club club){
        return clubRepo.save(club);
    }

    public void deleteClub(String name){ clubRepo.deleteClubByClubName(name); }

    public Club findClubByName(String name){
        return clubRepo.findClubByClubName(name).orElseThrow(() -> new IllegalArgumentException("Club called " + name + " not found"));
    }

}
