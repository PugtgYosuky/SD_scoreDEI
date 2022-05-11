package com.example.scoredei.data.events;

import com.example.scoredei.data.Event;
import com.example.scoredei.data.Game;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class EventInterrupt extends Event {

    public EventInterrupt() {
        super();
    }

    public EventInterrupt(Game game){
        super(game);
    }
    
    public EventInterrupt(Game game, Date time) {
        super(game, time);
    }

}
