package com.example.scoredei.data.events;

import com.example.scoredei.data.Event;
import com.example.scoredei.data.Game;
import com.example.scoredei.data.types.EventType;

import javax.persistence.Entity;
import java.util.Date;

/**
 * EventResume is a subclass of Event that represents a resume event
 */
@Entity
public class EventResume extends Event {

    public EventResume() {
        super(EventType.RESUME);
    }
    
    public EventResume(Game game) {
        super(EventType.RESUME, game);
    }

    public EventResume(Game game, Date time) {
        super(EventType.RESUME, game, time);
    }

}
