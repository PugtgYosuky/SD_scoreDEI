package com.example.scoredei.data.events;

import com.example.scoredei.data.Event;
import com.example.scoredei.data.Game;
import com.example.scoredei.data.Player;
import com.example.scoredei.data.types.EventType;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * EventYellowCard is a subclass of Event that represents a yellow card, and it has a many-to-one relationship with Player
 */
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
     * The toString() method returns a string representation of the object
     * 
     * @return The player's name and the event type.
     */
    @Override
    public String toString() {
        return "EventYellowCard{" +
                "player=" + player +
                '}';
    }

}
