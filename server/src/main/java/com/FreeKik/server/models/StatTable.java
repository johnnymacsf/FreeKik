package com.FreeKik.server.models;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MapKeyColumn;

import java.util.HashMap;

@Embeddable
public class StatTable {
    //private String name;
    private String gamesPlayed;

    @ElementCollection
    @CollectionTable(name="club_stats")
    @MapKeyColumn(name="label")
    private HashMap<String, HashMap<String, String>> table;

    public StatTable(){
        this.table = new HashMap<>();
    }
    /*
    public StatTable(String name){
        this.name = name;
        this.table = new HashMap<>();
    }
     */
    public StatTable(String gp){
        //this.name = name;
        this.gamesPlayed = gp;
        this.table = new HashMap<>();
    }
    /*
    public void setName(String name){
        this.name = name;
    }
     */
    public void setGamesPlayed(String gp){
        this.gamesPlayed = gp;
    }

    public void addStat(String label, String statType, String value){
        table.computeIfAbsent(label, p -> new HashMap<>()).put(statType, value);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        //sb.append("{" + name + ": {Games played: " + gamesPlayed + "}, " + System.lineSeparator());
        sb.append("{Statistics: {Games played: " + gamesPlayed + "}, " + System.lineSeparator());
        table.forEach((label,v)-> {
            sb.append(label + ": {");
            v.forEach((type, val) -> {
                sb.append(type + ": " + val + ", ");
            });
            sb.append("}, " + System.lineSeparator());
        });
        sb.append("}");
        return sb.toString();
    }
}
