package com.example.scoredei.data.events;

import com.example.scoredei.data.Event;
import com.example.scoredei.data.Game;
import com.example.scoredei.data.types.EventType;

import javax.persistence.Entity;
import java.util.Date;

/**
 * EventStart is a subclass of Event that represents the start of a game
 */
@Entity
public class EventStart extends Event {

    public EventStart() {
        super(EventType.START);
    }

    public EventStart(Game game) {
        super(EventType.START, game);
    }
    
    public EventStart(Game game, Date time) {
        super(EventType.START, game, time);
    }

}
