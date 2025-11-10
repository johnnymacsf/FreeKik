package com.FreeKik.server.Handlers;

import com.FreeKik.server.WebScraping.Scraper;
import com.FreeKik.server.models.Club;
import com.FreeKik.server.models.StatTable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.util.HashMap;

public class ClubHandler {
    private final WebDriver driver;
    private final Scraper scraper;

    public ClubHandler(){
        driver = new SafariDriver();
        scraper = new Scraper(driver);
    }

    public HashMap<String, Club> getAllClubs(){
        return scraper.scrapeForTeams();
    }

    public StatTable getStatTable(String url){
        return scraper.scrapeForTeamStats(url);
    }

    public void setStatTable(Club club){
        club.setTable(scraper.scrapeForTeamStats(club.getUrl()));
    }

    public void setAllStatTables(HashMap<String, Club> clubs){
        clubs.forEach((name, club) -> {
            setStatTable(club);
        });
    }

    public void shutDownDriver(){
        driver.quit();
    }
}
