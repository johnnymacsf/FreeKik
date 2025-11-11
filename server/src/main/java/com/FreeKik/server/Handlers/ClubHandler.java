package com.FreeKik.server.Handlers;

import com.FreeKik.server.WebScraping.Scraper;
import com.FreeKik.server.models.Club;
import com.FreeKik.server.models.StatTable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ClubHandler {
    private WebDriver driver;
    private Scraper scraper;

    private void ensureScraper() {
        if(driver == null){
            driver = new SafariDriver();
            scraper = new Scraper(driver);
        }
    }

    public HashMap<String, Club> getAllClubs(){
        ensureScraper();
        return scraper.scrapeForTeams();
    }

    public StatTable getStatTable(String url){
        ensureScraper();
        return scraper.scrapeForTeamStats(url);
    }

    public void setStatTable(Club club){
        ensureScraper();
        club.setTable(scraper.scrapeForTeamStats(club.getUrl()));
    }

    public void setAllStatTables(HashMap<String, Club> clubs){
        ensureScraper();
        clubs.forEach((name, club) -> {
            setStatTable(club);
        });
    }

    public HashMap<String, Club> cleanClubMap(HashMap<String, Club> clubMap){
        HashMap<String, Club> newClubMap = new HashMap<>();
        clubMap.forEach((name, club) -> {
            newClubMap.computeIfAbsent(name.toLowerCase().replaceAll(" ", ""), p -> club);
        });
        return newClubMap;
    }

    public void shutDownDriver(){
        if(driver != null){
            driver.quit();
        }
    }
}
