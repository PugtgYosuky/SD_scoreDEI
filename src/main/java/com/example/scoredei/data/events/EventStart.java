package com.example.scoredei.data.events;

import com.example.scoredei.data.Event;
import com.example.scoredei.data.Game;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class EventStart extends Event {

    public EventStart() {
        super();
    }

    public EventStart(Game game) {
        super(game);
    }
    
    public EventStart(Game game, Date time) {
        super(game, time);
    }

}
