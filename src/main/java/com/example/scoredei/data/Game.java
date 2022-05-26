package com.example.scoredei.data;

import com.example.scoredei.data.events.EventRedCard;
import com.example.scoredei.data.events.EventYellowCard;
import com.example.scoredei.data.types.EventType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Comparator;
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
        this.events.sort(Comparator.comparing(Event::getTime).reversed());
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

    public Event getLastEvent() {
        if(this.events.size() == 0)
            return null;
        this.events.sort(Comparator.comparing(Event::getTime).reversed());
        return this.events.get(0);
    }

    public int getYellowCards(Player player) {
        int count = 0;
        for(Event e : this.events) {
            if(e.getType() == EventType.YELLOW_CARD) {
                EventYellowCard event = (EventYellowCard) e;
                if(event.getPlayer().equals(player))
                    count++;
            }
        }
        return count;
    }

    public boolean hasRedCards(Player player) {
        for(Event e : this.events) {
            if(e.getType() == EventType.RED_CARD) {
                EventRedCard event = (EventRedCard) e;
                if(event.getPlayer().equals(player))
                    return true;
            }
        }
        return false;
    }

    public boolean isRunning() {
        return !isFinish() && this.events.size() > 0;
    }

    public boolean notStarted() {
        return this.events.size() == 0;
    }

    public boolean isFinish() {
        if(this.events.size() == 0)
            return false;
        return this.getLastEvent().getType() == EventType.END;
    }

}
