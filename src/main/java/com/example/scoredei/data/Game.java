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
    @ManyToOne
    private Team teamA;
    @ManyToOne
    private Team teamB;
    private String location;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date start;
    @OneToMany(mappedBy = "game")
    private List<Event> events;

    public Game() {

    }

    public Game(Team teamA, Team teamB, String location, Date start) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.location = location;
        this.start = start;
        this.events = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public Team getTeamA() {
        return teamA;
    }

    public Team getTeamB() {
        return teamB;
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

    public void setId(int id){
        this.id = id;
    }

    public void setTeamA(Team teamA) {
        this.teamA = teamA;
    }

    public void setTeamB(Team teamB) {
        this.teamB = teamB;
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
