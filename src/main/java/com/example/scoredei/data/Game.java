package com.example.scoredei.data;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@XmlRootElement
public class Game {

    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @ManyToMany
    private List<Team> teams;
    private String location;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date start;
    @OneToMany(mappedBy = "game")
    private List<Event> events;

    public Game() {
        this.teams = new ArrayList<>();
        this.events = new ArrayList<>();
        
    }

    public Game(Team teamA, Team teamB, String location, Date start) {
        this.teams = new ArrayList<>();
        this.teams.add(teamA);
        this.teams.add(teamB);
        this.location = location;
        this.start = start;
        this.events = new ArrayList<>();
    }

    public void addTeam(Team team) {
        teams.add(team);
    }

    public int getId() {
        return id;
    }

    public Team getTeamA() {
        return teams.get(0);
    }

    public Team getTeamB() {
        return teams.get(1);
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams){
        this.teams = teams;
    }

    public String getLocation() {
        return location;
    }

    public Date getStart() {
        return start;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setTeamA(Team teamA) {
        this.teams.set(0, teamA);
    }

    public void setTeamB(Team teamB) {
        this.teams.set(1, teamB);
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }

}
