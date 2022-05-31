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

    // This is a constructor. It is called when you create a new instance of the class.
    public Game() {
        this.teams = new ArrayList<>();
        this.events = new ArrayList<>();
        this.start = null;
    }

    // This is a constructor. It is called when you create a new instance of the class.
    public Game(Team teamA, Team teamB, String location, Date start) {
        this.teams = new ArrayList<>();
        this.teams.add(teamA);
        this.teams.add(teamB);
        this.location = location;
        this.start = start;
        this.events = new ArrayList<>();
    }

    /**
     * This function adds a team to the list of teams
     * 
     * @param team The team to add to the list of teams.
     */
    public void addTeam(Team team) {
        teams.add(team);
    }

    /**
     * This function returns the id of the object
     * 
     * @return The id of the object.
     */
    public int getId() {
        return id;
    }

    /**
     * This function returns the first team in the list of teams
     * 
     * @return The first team in the list.
     */
    public Team getTeamA() {
        return teams.get(0);
    }

    /**
     * This function returns the second team in the list of teams
     * 
     * @return The second team in the list.
     */
    public Team getTeamB() {
        return teams.get(1);
    }

    /**
     * This function returns a list of teams
     * 
     * @return A list of teams.
     */
    public List<Team> getTeams() {
        return teams;
    }

    /**
     * This function sets the teams of a league
     * 
     * @param teams The list of teams to be displayed in the list.
     */
    public void setTeams(List<Team> teams){
        this.teams = teams;
    }

    /**
     * This function returns the location of the event
     * 
     * @return The location of the event.
     */
    public String getLocation() {
        return location;
    }

    /**
     * This function returns the start date of the event
     * 
     * @return The start date.
     */
    public Date getStart() {
        return start;
    }

    /**
     * This function sorts the events in the list by time in descending order
     * 
     * @return A list of events sorted by time in descending order.
     */
    public List<Event> getEvents() {
        this.events.sort(Comparator.comparing(Event::getTime).reversed());
        return events;
    }

    /**
     * This function sets the events of the current object to the events passed in as a parameter
     * 
     * @param events The list of events to be displayed in the calendar.
     */
    public void setEvents(List<Event> events) {
        this.events = events;
    }

    /**
     * This function sets the id of the object to the id passed in as a parameter
     * 
     * @param id The id of the user.
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * This function sets the first element of the teams array to the teamA parameter
     * 
     * @param teamA The team to set as team A
     */
    public void setTeamA(Team teamA) {
        this.teams.set(0, teamA);
    }
    
    /**
     * This function sets the second team in the list of teams to the team passed in as a parameter
     * 
     * @param teamB The team to set as team B.
     */
    public void setTeamB(Team teamB) {
        this.teams.set(1, teamB);
    }

    /**
     * This function sets the location of the user
     * 
     * @param location The location of the file.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * This function sets the start date of the event
     * 
     * @param start The start date of the event.
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * This function adds an event to the events list
     * 
     * @param event The event to add to the list of events.
     */
    public void addEvent(Event event) {
        this.events.add(event);
    }

    /**
     * This function returns the last event in the list of events
     * 
     * @return The last event in the list of events.
     */
    public Event getLastEvent() {
        if(this.events.size() == 0)
            return null;
        this.events.sort(Comparator.comparing(Event::getTime).reversed());
        return this.events.get(0);
    }

    /**
     * "Count the number of yellow cards a player has received."
     * 
     * The function is a bit long, but it's not too bad. It's not too hard to understand what's going
     * on
     * 
     * @param player The player to get the yellow cards for
     * @return The number of yellow cards a player has received.
     */
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

    /**
     * "If the player has a red card, return true."
     * 
     * The function is a bit more complicated than that, but that's the gist of it
     * 
     * @param player The player to check for red cards
     * @return A boolean value
     */
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

    /**
     * "If the event is not finished and the event has at least one event, then the event is running."
     * 
     * The function isRunning() is a boolean function, so it returns either true or false
     * 
     * @return The method is returning a boolean value.
     */
    public boolean isRunning() {
        return !isFinish() && this.events.size() > 0;
    }

    /**
     * This function returns true if the size of the events array is 0
     * 
     * @return The size of the events array.
     */
    public boolean notStarted() {
        return this.events.size() == 0;
    }
    
    /**
     * If the size of the events list is 0, return false. Otherwise, return whether the last event in
     * the list is of type END
     * 
     * @return The last event in the list of events.
     */
    public boolean isFinish() {
        if(this.events.size() == 0)
            return false;
        return this.getLastEvent().getType() == EventType.END;
    }

    /**
     * If the location is not empty, the start date is not null, and the number of teams is 2, then
     * return true.
     * 
     * @return The method isCompleted() returns a boolean value.
     */
    public boolean isCompleted() {
        return !this.location.isEmpty()
            && this.start != null
            && this.teams.size() == 2;
    }

}
