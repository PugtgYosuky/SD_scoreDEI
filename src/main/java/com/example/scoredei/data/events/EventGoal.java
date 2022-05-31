package com.example.scoredei.data.events;

import com.example.scoredei.data.Event;
import com.example.scoredei.data.Game;
import com.example.scoredei.data.Player;
import com.example.scoredei.data.Team;
import com.example.scoredei.data.types.EventType;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * EventGoal is a subclass of Event, and it has a many-to-one relationship with Player and Team.
 */
@Entity
public class EventGoal extends Event {

    @ManyToOne
    private Player player;
    @ManyToOne
    private Team team;

    public EventGoal() {
        super(EventType.GOAL);
    }

    public EventGoal(Game game){
        super(EventType.GOAL,game);
    }
    public EventGoal(Game game, Date time) {
        super(EventType.GOAL, game, time);
    }
    
    public EventGoal(Game game, Date time, Player player, Team team) {
        super(EventType.GOAL, game, time);
        this.player = player;
        this.team = team;
    }

    /**
     * This function returns the player object
     * 
     * @return The player object.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * This function sets the player variable to the player variable passed in
     * 
     * @param player The player that is being checked.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * This function returns the team of the player
     * 
     * @return The team object.
     */
    public Team getTeam() {
        return team;
    }

    /**
     * This function sets the team of the player
     * 
     * @param team The team that the player is on.
     */
    public void setTeam(Team team) {
        this.team = team;
    }

    /**
     * The toString() method returns a string representation of the object
     * 
     * @return The player and team that scored the goal.
     */
    @Override
    public String toString() {
        return "EventGoal{" + "player=" + player + ", team=" + team + '}';
    }

}
