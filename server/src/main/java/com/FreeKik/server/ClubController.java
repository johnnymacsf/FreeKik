package com.FreeKik.server;

import com.FreeKik.server.Handlers.ClubHandler;
import com.FreeKik.server.models.Club;
import com.FreeKik.server.service.ClubService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/club")
public class ClubController {
    private final ClubService clubService;
    private final ClubHandler clubHandler;

    public ClubController(ClubService clubService, ClubHandler clubHandler) {
        this.clubService = clubService;
        this.clubHandler = clubHandler;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/findAll")
    public ResponseEntity<List<Club>> getAllClubs() {
        List<Club> clubs = clubService.findAllClubs();
        return new ResponseEntity<>(clubs, HttpStatus.OK);
    }

    @GetMapping("/find/{name}")
    public ResponseEntity<Club> getMatchById(@PathVariable("name") String name) {
        Club club = clubService.findClubByName(name);
        return new ResponseEntity<>(club, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Club> addClub(@RequestBody Club club) {
        Club newClub = clubService.addClub(club);
        return new ResponseEntity<>(newClub, HttpStatus.CREATED);
    }

    @PostMapping("/populate")
    public List<ResponseEntity<Club>> addAllClubs() {
        ArrayList<ResponseEntity<Club>> list = new ArrayList<>();
        HashMap<String, Club> clubs = clubHandler.getAllClubs();
        clubHandler.setAllStatTables(clubs);
        clubs.forEach((name, club) -> list.add(addClub(club)));
        System.out.println(clubs);
        return list;
    }

    @GetMapping("/stats/{name}")
    public ResponseEntity<Club> getMatchOdds(@PathVariable("name") String name){
        Club club = clubService.findClubByName(name);
        clubHandler.setStatTable(club);
        return addClub(club);
    }
}