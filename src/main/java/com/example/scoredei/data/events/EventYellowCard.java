package com.example.scoredei.data.events;

import com.example.scoredei.data.Event;
import com.example.scoredei.data.Game;
import com.example.scoredei.data.Player;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class EventYellowCard extends Event {

    @ManyToOne
    public Player player;

    public EventYellowCard() {
        super();
    }

    public EventYellowCard(Game game) {
        super(game);
    }
    
    public EventYellowCard(Game game, Date time, Player player) {
        super(game, time);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "EventYellowCard{" +
                "player=" + player +
                '}';
    }

}
