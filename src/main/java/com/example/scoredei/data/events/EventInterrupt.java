package com.example.scoredei.data.events;

import com.example.scoredei.data.Event;
import com.example.scoredei.data.Game;
import com.example.scoredei.data.types.EventType;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class EventInterrupt extends Event {

    public EventInterrupt() {
        super(EventType.INTERRUPT);
    }

    public EventInterrupt(Game game){
        super(EventType.INTERRUPT, game);
    }
    
    public EventInterrupt(Game game, Date time) {
        super(EventType.INTERRUPT, game, time);
    }

}
