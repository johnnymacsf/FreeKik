package com.FreeKik.server.WebScraping;

import com.FreeKik.server.models.Club;
import com.FreeKik.server.models.StatTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scraper {
    private final WebDriver driver;

    public Scraper(WebDriver d){
        this.driver = d;
    }

    public StatTable scrapeForTeamStats(String url) {
        //StringBuilder sb = new StringBuilder();
        //WebDriver driver = new SafariDriver();
        StatTable stats = new StatTable();
        try {
            if (!url.endsWith("stats")) {
                Pattern p = Pattern.compile("(.*)/(.*)");
                Matcher m = p.matcher(url);
                if (m.find()) {
                    //System.out.println("1: " + m.group(1) + "2: " + m.group(2));
                    url = m.group(1) + "/stats";
                }
                //System.out.println(url);
                //return stats;
            }

            driver.get(url);

            Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(d -> driver.findElement(By.cssSelector("#main-content > div.page-wrapper > div:nth-child(2) > div.col-8.col-8-wide.col-6-tab > section > section")).isDisplayed()
                    && driver.findElement(By.cssSelector("#main-content > div.page-wrapper > div:nth-child(2) > div.col-4.col-4-wide.col-6-tab > section > div")).isDisplayed()
                    && driver.findElement(By.cssSelector("#main-content > div.page-wrapper > div:nth-child(2) > div.col-4.col-4-wide.col-6-tab > section > div > div > header > div.club-profile-header__bottom > h1")).isDisplayed()
                    && driver.findElement(By.cssSelector("#main-content > div.page-wrapper > div:nth-child(2) > div.col-8.col-8-wide.col-6-tab > section > section > div > div > div > div.club-profile__panel.club-profile__panel--stats > div.club-profile__stats > section.profile-stat-cards-container")).isDisplayed()
                    && driver.findElement(By.cssSelector("#main-content > div.page-wrapper > div:nth-child(2) > div.col-8.col-8-wide.col-6-tab > section > section > div > div > div > div.club-profile__panel.club-profile__panel--stats > div.club-profile__stats > section.profile-stat-lists-container")).isDisplayed());

            //stats.setName(driver.findElement(By.cssSelector("#main-content > div.page-wrapper > div:nth-child(2) > div.col-4.col-4-wide.col-6-tab > section > div > div > header > div.club-profile-header__bottom > h1")).getText());

            List<WebElement> cards = driver.findElements(By.cssSelector("#main-content > div.page-wrapper > div:nth-child(2) > div.col-8.col-8-wide.col-6-tab > section > section > div > div > div > div.club-profile__panel.club-profile__panel--stats > div.club-profile__stats > section.profile-stat-cards-container > div"));

            for (WebElement e : cards) {
                String field = e.findElement(By.cssSelector("h4")).getText();
                String val = e.findElement(By.cssSelector("p")).getText();
                //System.out.println(field + ": " + val);
                if (field.toLowerCase().replace(" ", "").equals("gamesplayed")) {
                    stats.setGamesPlayed(val);
                }
                if (field.toLowerCase().replace(" ", "").equals("goalsconceded")) {
                    stats.addStat("Defence", field, val);
                }
            }

            List<WebElement> lists = driver.findElements(By.cssSelector("#main-content > div.page-wrapper > div:nth-child(2) > div.col-8.col-8-wide.col-6-tab > section > section > div > div > div > div.club-profile__panel.club-profile__panel--stats > div.club-profile__stats > section.profile-stat-lists-container > div"));

            for (WebElement l : lists) {
                String label = l.findElement(By.cssSelector("h4")).getText();
                List<WebElement> subLists = l.findElements(By.cssSelector("ul > li"));
                for (WebElement s : subLists) {
                    stats.addStat(label, s.findElement(By.cssSelector("p.profiles-stats-list__stat-label")).getText(), s.findElement(By.cssSelector("p.profiles-stats-list__stat-value")).getText());
                }
            }

            //return stats;
        } catch (Exception e){
            System.out.println(e);
        }
        return stats;
//        } finally {
//            driver.close();
//        }
    }

    public HashMap<String, Club> scrapeForTeams() {
        //StringBuilder sb = new StringBuilder();
        //WebDriver driver = new SafariDriver();
        HashMap<String, Club> clubs = new HashMap<>();
        try {
            driver.get("https://www.premierleague.com/en/clubs");

            Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(d -> driver.findElement(By.cssSelector("#main-content > div.page-wrapper > div.default-template > div > section.club-listings-grid")).isDisplayed());
            List<WebElement> cards = driver.findElements(By.cssSelector("#main-content > div.page-wrapper > div.default-template > div > section.club-listings-grid > ul > li"));

            for(WebElement e : cards){
                Club club = new Club();
                club.setUrl(e.findElement(By.cssSelector("div.club-listings-card__team > a")).getAttribute("href"));
                club.setImage(e.findElement(By.cssSelector("div.club-listings-card__team > div > div > img")).getAttribute("src"));
                club.setName(e.findElement(By.cssSelector("div.club-listings-card__team")).getText());

                //sb.append(club);
                clubs.put(club.getName(), club);
            }
            //return clubs;
        } catch (Exception e){
            System.out.println(e);
        }//finally {
           // driver.close();
        //}
        return clubs;
    }
}
