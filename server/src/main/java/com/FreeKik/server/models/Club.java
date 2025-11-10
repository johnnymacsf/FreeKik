package com.FreeKik.server.models;

import com.FreeKik.server.WebScraping.Scraper;

public class Club {
    private String url;
    private String name;
    private String image;
    private StatTable table;

    public Club(){}
    public Club(String url, String name, String img) {
        this.url = url;
        this.name = name;
        this.image = img;
    }

    public String getUrl() { return url; }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getName() { return name; }
    public void setName(String name){
        this.name = name;
    }
    public String getImage() { return image; }
    public void setImage(String img){
        this.image = img;
    }

    public StatTable getTable() { return this.table; }
    public void setTable(StatTable table){
        this.table = table;
    }

    @Override
    public String toString() {
        return "{ \"name\": \"" + name+ "\", "
                + " \"image\": \"" + image + "\", "
                + "\"url\": \"" + url + "\", "
                + table + "} ";
    }
}
