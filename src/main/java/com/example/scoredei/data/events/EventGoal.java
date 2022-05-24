package com.example.scoredei.data.events;

import com.example.scoredei.data.Event;
import com.example.scoredei.data.Game;
import com.example.scoredei.data.Player;
import com.example.scoredei.data.Team;
import com.example.scoredei.data.types.EventType;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

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
    
    public EventGoal(Game game, Date time, Player player, Team team) {
        super(EventType.GOAL, game, time);
        this.player = player;
        this.team = team;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "EventGoal{" + "player=" + player + ", team=" + team + '}';
    }

}
