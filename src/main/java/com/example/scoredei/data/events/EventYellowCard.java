package com.example.scoredei.data.events;

import com.example.scoredei.data.Event;
import com.example.scoredei.data.Game;
import com.example.scoredei.data.Player;
import com.example.scoredei.data.types.EventType;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class EventYellowCard extends Event {

    @ManyToOne
    public Player player;

    public EventYellowCard() {
        super(EventType.YELLOW_CARD);
    }

    public EventYellowCard(Game game) {
        super(EventType.YELLOW_CARD, game);
    }

    public EventYellowCard(Game game, Date time) {
        super(EventType.YELLOW_CARD, game, time);
    }
    
    public EventYellowCard(Game game, Date time, Player player) {
        super(EventType.YELLOW_CARD, game, time);
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
