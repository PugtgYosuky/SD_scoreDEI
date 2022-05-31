package com.example.scoredei.data.events;

import com.example.scoredei.data.Event;
import com.example.scoredei.data.Game;
import com.example.scoredei.data.Player;
import com.example.scoredei.data.types.EventType;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * EventRedCard is a subclass of Event.
 */
@Entity
public class EventRedCard extends Event {

    @ManyToOne
    private Player player;

    public EventRedCard() {
        super(EventType.RED_CARD);
    }
    public EventRedCard(Game game, Date time) {
        super(EventType.RED_CARD, game, time);
    }

    public EventRedCard(Game game, Date time, Player player) {
        super(EventType.RED_CARD, game, time);
        this.player = player;
    }
    
    public EventRedCard(Game game) {
        super(EventType.RED_CARD, game);
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
        return "EventRedCard{" + "player=" + player + '}';
    }

}
